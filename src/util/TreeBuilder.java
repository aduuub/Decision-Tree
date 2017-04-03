package util;

import main.Patient;
import node.LeafNode;
import node.Node;
import node.ParentNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Adam on 24/03/17.
 */
public class TreeBuilder {

    public static final String INDENT_SPACING = "  ";

    private String baselineOutcome;
    private double baseLineProbability;

    private List<String> attributeNames;

    private Node rootNode;


    /**
     * Create a new builder
     * @param trainingData
     * @param attributeNames
     */
    public TreeBuilder(List<Patient> trainingData, List<String> attributeNames){

//        int count = 0;
//        int femaleIndex = attributeNames.indexOf("FEMALE");
//        for(Patient p : trainingData){
//            if(p.getOutcome().equals("live")){
//                count++;
//            }
//        }
//        System.out.println(count + " Live");

        // Calculate baseline class and probability
        baselineOutcome = mostProbableAttribute(trainingData);
        baseLineProbability = attributeProbability(trainingData, baselineOutcome);

        this.attributeNames = attributeNames;
        List<Patient> data = new ArrayList<>(trainingData);

        List<Integer> attributesInt = attributeNames.stream().map(a -> attributeNames.indexOf(a)).collect(Collectors.toList());
        rootNode = buildTree(data, attributesInt);


    }


    /**
     * Builds a tree using the patients and attributes.
     * @param patients
     * @param attributes
     * @return - the head of the tree
     */
    private Node buildTree(List<Patient> patients, List<Integer> attributes){
        if(patients.isEmpty()){
            // If patients are empty return a leaf node with the overall most probable attribute
            return new LeafNode(baselineOutcome, baseLineProbability);

        }else if(NodePurityCalculator.areInstancesPure(patients)){
            // If patients are pure return a leaf node with probability of one
            return new LeafNode(patients.get(0).getOutcome(), 1);

        }else if(attributes.isEmpty()){
            // If if attributes are empty return new leaf node containing the name and probability of the majority class
            String mostProbableOutcome = mostProbableAttribute(patients);
            double probability = attributeProbability(patients, mostProbableOutcome);
            return new LeafNode(mostProbableOutcome, probability);

        }else{
            // Else find the best attribute
            return findBestAttribute(patients, attributes);
        }
    }


    /**
     * Finds the best attribute in the patients. Best attribute determined by the purity of the data
     * @param patients
     * @param attributes
     * @return
     */
    private Node findBestAttribute(List<Patient> patients, List<Integer> attributes){
        // Init
        int bestAttribute = -1;
        double lowestImpurity = Double.POSITIVE_INFINITY;
        List<Patient> bestTruePatients = null;
        List<Patient> bestFalsePatients = null;

        // For each attribute
        for(int i = 0; i < attributes.size(); i++){
            int attributeIndex = attributes.get(i);

            // Separate into two sets
            SubListBuilder subListBuilder = new SubListBuilder(patients, attributeIndex);
            List<Patient> trueAttributes = subListBuilder.getTrueAttributes();
            List<Patient> falseAttributes = subListBuilder.getFalseAttributes();

            // Calculate purity so far
            NodePurityCalculator purityCalculator = new NodePurityCalculator(trueAttributes, falseAttributes);
            double impurity = purityCalculator.calcWeightedImpurity();

            // If purity of this set is the best so far
            if(impurity < lowestImpurity){
                // Update best attributes
                bestAttribute = attributeIndex;
                lowestImpurity = impurity;
                bestTruePatients = trueAttributes;
                bestFalsePatients = falseAttributes;
            }
        }

        // Remove used attribute
        String bestAttributeName = attributeNames.get(bestAttribute);
        int index = attributes.indexOf(bestAttribute);
        attributes.remove(index);

        // Build subtree using the remaining attributes
        Node right = buildTree(bestFalsePatients, attributes);
        Node left = buildTree(bestTruePatients, attributes);

        // Add it back in for future recursions
        attributes.add(bestAttribute);

        return new ParentNode(bestAttributeName, left, right);
    }


    // Methods below are for calculate most probably and probability of an attribute

    /**
     * Finds the most likely attribute that will occur
     * @param patients
     * @return
     */
    private static String mostProbableAttribute(List<Patient> patients){
        Map<String, Integer> attributeToVotes = new HashMap<>(); // Attribute -> Vote count

        for(Patient patient : patients){
            String outcome = patient.getOutcome();
            if(attributeToVotes.containsKey(outcome)){
                int count = attributeToVotes.get(outcome);
                attributeToVotes.put(outcome, count+1);
            }else{
                attributeToVotes.put(outcome, 1);
            }
        }

        return Collections.max(attributeToVotes.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
    }


    /**
     * Calculates the probability of an outcome
     * @param patients
     * @param outcome
     * @return
     */
    private double attributeProbability(List<Patient> patients, String outcome){
        if(patients.size() == 0)
            return 0;

        int count = 0;
        for(Patient patient : patients){
            if(patient.getOutcome().equals(outcome))
                count++;
        }

        return (count + 0.0) / patients.size();
    }

    public Node getRootNode(){
        return rootNode;
    }

    public String getBaselineOutcome() {
        return baselineOutcome;
    }

    public double getBaseLineProbability() {
        return baseLineProbability;
    }
}

