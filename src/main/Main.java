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

    private Node headNode;


    public Main(String trainingFileName, String testingFileName){
        // Parse in the data and build the tree using the training data
        headNode = initTree(trainingFileName, testingFileName);

        // Run the tests
        headNode.report("", attributeNames);
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
        TreeBuilder treeBuilder = new TreeBuilder(trainingData, attributeNamesClone);
        return treeBuilder.getRootNode();
    }


    public static void main(String args[]){
        if(args.length != 2){
            System.out.println("Invalid program arguments. \n Arguments: \"TrainingFileName\" \"TestingFileName\"");

        }else{
            new Main(args[0], args[1]);
        }
    }
}
