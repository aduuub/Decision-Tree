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
    }


    /**
     * Constructs the tree and sets the fields
     * @param trainingFileName
     * @param testingFileName
     * @return
     */
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


    /**
     * Runs the tests on the test data
     * @return - percentage correct from running the test data on the training data
     */
    private double runTests(){
        // Print accuracy
        System.out.println("\nBaseline probability: " + treeBuilder.getBaseLineProbability());
        System.out.println("Baseline attribute: " + treeBuilder.getBaselineOutcome());
        System.out.println("Tree: \n");

        // Print the tree
        headNode.report("");

        // Run the tests d
        int correctlyGuessed = 0;
        int predictedToDie = 0;
        int actuallyDied = 0;
        for(Patient p : testingData) {
            String outcome = headNode.traverse(p, attributeNames);
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

        double percentage = ((double)correctlyGuessed) / ((double)testingData.size());
        System.out.println("\nPatients predicted to die: " + predictedToDie + ". Actually died: " + actuallyDied);
        System.out.println("Patients predicted to live: " + (testingData.size() - predictedToDie) + ". Actually lived: " +
                (testingData.size() - actuallyDied));
        System.out.println("Percentage correct: " + percentage);
        return percentage;
    }


    /**
     * Run the program!
     * @param args
     */
    public static void main(String args[]){
        if(args.length == 0){
            run10Tests();
        }
        else if(args.length != 2){
            System.out.println("Invalid program arguments. \n Arguments: \"TrainingFileName\" \"TestingFileName\" \n" +
                    "Otherwise run with no arguments to run all 10 tests");

        }else{
            System.out.println("Running on specified files");
            new Main(args[0], args[1]).runTests();
        }
    }

    /**
     * Runs the 10 tests using hepatitis-test-run and hepatitis-training-run files
     */
    public static void run10Tests(){
        System.out.println("Running 10 tests");

        List<Double> percentage = new ArrayList<>();
        for(int i=1; i <= 10; i++){
            String num = (i < 10) ? "0"+i : String.valueOf(i);
            String test = "hepatitis-test-run" + num + ".dat";
            String train = "hepatitis-training-run" + num + ".dat";

            Main main = new Main(train, test);
            double perc = main.runTests();
            System.out.println(perc);
            percentage.add(perc);
        }

        double total = 0;
        for(Double d : percentage){
            total += d;
            System.out.println(d*100);
        }
        System.out.println("Total " + total*100);

        total /= percentage.size();
        System.out.println("Total " + total);
    }
}
