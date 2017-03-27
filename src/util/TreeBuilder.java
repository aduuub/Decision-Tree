package util;

import main.Patient;
import node.LeafNode;
import node.Node;
import node.ParentNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adam on 24/03/17.
 */
public class TreeBuilder {

    private int baselineAttribute;
    private double baseLineProbability;

    private Node rootNode;

    /**
     * Create a new builder
     * @param trainingData
     * @param attributeNames
     */
    public TreeBuilder(List<Patient> trainingData, List<String> attributeNames){

        // Calculate baseline class and probability
        baselineAttribute = TreeBuilder.mostProbableAttribute(trainingData);
        baseLineProbability = attributeProbability(trainingData, baselineAttribute);

        rootNode = buildTree(trainingData, attributeNames);
    }


    /**
     * Builds a tree using the patients and attributes.
     * @param patients
     * @param attributes
     * @return - the head of the tree
     */
    private Node buildTree(List<Patient> patients, List<String> attributes){

        if(patients.isEmpty()){
            // If patients are empty return a leaf node with the overall most probable attribute
            return new LeafNode(baselineAttribute, baseLineProbability);

        }if(NodePurityCalculator.areInstancesPure(patients)){
            // If patients are pure return a leaf node with probability of one
            return new LeafNode(patients.get(0).getCategory(), 1);


        }if(attributes.isEmpty()){
            // If if attributes are empty return new leaf node containing the name and probability of the majority class
            int mostProbableAttribute = mostProbableAttribute(patients);
            double probability = attributeProbability(patients, mostProbableAttribute);
            return new LeafNode(mostProbableAttribute, probability);


        }else{
            // Else find the best attribute
            return findBestAttribute(patients, attributes);
        }
    }

    /**
     * Finds the most likely attribute that will occur
     * @param patients
     * @return
     */
    private static int mostProbableAttribute(List<Patient> patients){
        Map<Integer, Integer> attributeToVotes = new HashMap<>(); // Attribute -> Vote count

        for(Patient patient : patients){
            int attribute = patient.getCategory();
            if(attributeToVotes.containsKey(attribute)){
                int count = attributeToVotes.get(attribute);
                attributeToVotes.put(attribute, count+1);
            }else{
                attributeToVotes.put(attribute, 1);
            }
        }

        return Collections.max(attributeToVotes.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
    }

    /**
     * Calculates the probability of an attribute
     * @param patients
     * @param attribute
     * @return
     */
    private double attributeProbability(List<Patient> patients, int attribute){
        if(patients.size() == 0)
            return 0;

        int count = 0;
        for(Patient patient : patients){
            if(patient.getCategory() == attribute)
                count++;
        }

        return (count + 0.0) / patients.size();
    }

    /**
     * Finds the best attribute in the patients. Best attribute determined by the purity of the data
     * @param patients
     * @param attributes
     * @return
     */
    private ParentNode findBestAttribute(List<Patient> patients, List<String> attributes){
        int bestAttribute = 0;
        double bestPurity = Double.POSITIVE_INFINITY;
        List<Patient> bestTruePatients = null;
        List<Patient> bestFalsePatients = null;

        // For each attribute
        for(int attributeIndex = 0; attributeIndex < attributes.size(); attributeIndex++){

            // Separate into two sets
            SubListAttributeBuilder subListBuilder = new SubListAttributeBuilder(patients, attributeIndex);
            List<Patient> trueAttributes = subListBuilder.getTrueAttributes();
            List<Patient> falseAttributes = subListBuilder.getFalseAttributes();

            // Calculate purity so far
            NodePurityCalculator purityCalculator = new NodePurityCalculator(attributeIndex, trueAttributes, falseAttributes);
            double purity = purityCalculator.calculateImpurity();

            // If purity of this set is the best so far
            if(purity < bestPurity){
                // Update best attributes
                bestAttribute = attributeIndex;
                bestPurity = purity;
                bestTruePatients = trueAttributes;
                bestFalsePatients = falseAttributes;
            }

        }
        // Remove used attribute
        attributes.remove(bestAttribute);

        // Build subtree using the remaining attributes
        Node left = buildTree(bestTruePatients, attributes);
        Node right = buildTree(bestFalsePatients, attributes);
        return new ParentNode(bestAttribute, left, right);
    }


    public Node getRootNode(){
        return rootNode;
    }

}
