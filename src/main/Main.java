package main;

import node.Node;
import util.Parser;
import util.TreeBuilder;

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
        initTree(trainingFileName, testingFileName);

        // Run the tests

    }


    private void initTree(String trainingFileName, String testingFileName){
        // Load training data
        Parser parser = new Parser(trainingFileName);
        trainingData = parser.getPatients();

        // Load testing data and names
        parser = new Parser(testingFileName);
        testingData = parser.getPatients();
        attributeNames = parser.getAttNames();
        categoryNames = parser.getCategoryNames();

        // Build the tree
        TreeBuilder treeBuilder = new TreeBuilder(trainingData, attributeNames);
        headNode = treeBuilder.getRootNode();
    }


    public static void main(String args[]){
        new Main(args[0], args[1]);
    }
}
