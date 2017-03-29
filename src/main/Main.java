package main;

import node.Node;
import util.Parser;
import util.TreeBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 24/03/17.
 */
public class Main {

    // Data
    private List<Patient> trainingData;
    private List<Patient> testingData;

    // Names
    private List<String> attributeNames;
    private List<String> categoryNames; // Live = 0, Die = 1

    // Tree
    private TreeBuilder treeBuilder;
    private Node headNode;


    public Main(String trainingFileName, String testingFileName){
        // Parse in the data and build the tree using the training data
        headNode = initTree(trainingFileName, testingFileName);

        //
        runTests();
    }


    private Node initTree(String trainingFileName, String testingFileName){
        // Load training data
        Parser parser = new Parser(trainingFileName);
        trainingData = parser.getPatients();

        // Load testing data and names
        parser = new Parser(testingFileName);
        testingData = parser.getPatients();
        attributeNames = parser.getAttNames();
        categoryNames = parser.getCategoryNames();

        List<String> attributeNamesClone = new ArrayList<>();
        attributeNamesClone.addAll(attributeNames);

        // Build the tree
        treeBuilder = new TreeBuilder(trainingData, attributeNamesClone);
        return treeBuilder.getRootNode();
    }


    private void runTests(){
        // Print accuracy
        System.out.println("\nBaseline probability: " + treeBuilder.getBaseLineProbability());
        System.out.println("Baseline attribute: " + treeBuilder.getBaselineOutcome());
        System.out.println("Tree: \n");

        // Print the tree
        headNode.report("");

        // Run the tests
        int correctlyGuessed = 0;
        int predictedToDie = 0;
        int actuallyDied = 0;
        for(Patient p : testingData) {
            String outcome = headNode.traverse(testingData.get(0), attributeNames);
            if(outcome.equals(p.getOutcome())){
                correctlyGuessed++;
            }

            // Prediction
            if(p.getOutcome().equals("die")) {
                actuallyDied++;
            }if(outcome.equals("die")){
                predictedToDie++;
            }

        }

        double percentage = (correctlyGuessed + 0.0) / testingData.size();
        System.out.println("\nPatients predicted to die: " + predictedToDie + ". Actually died: " + actuallyDied);
        System.out.println("Patients predicted to live: " + (testingData.size() - predictedToDie) + ". Actually lived: " +
                (testingData.size() - actuallyDied));
        System.out.println("Percentage correct: " + percentage);
    }


    public static void main(String args[]){
        if(args.length != 2){
            System.out.println("Invalid program arguments. \n Arguments: \"TrainingFileName\" \"TestingFileName\"");

        }else{
            new Main(args[0], args[1]);
        }
    }
}
