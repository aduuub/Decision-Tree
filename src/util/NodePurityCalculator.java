package util;

import main.Patient;

import java.util.List;

/**
 * Created by Adam on 24/03/17.
 * Calculates the purity of data sets, and counts the specified outcome
 */
public class NodePurityCalculator {

    private List<Patient> trueInstances, falseInstances;

    public NodePurityCalculator(List<Patient> truePatients, List<Patient> falsePatients) {
        this.trueInstances = truePatients;
        this.falseInstances = falsePatients;
    }


    /**
     * Calculate the impurity of the true and false data sets.
     * @return
     */
    public double calcWeightedImpurity(){
        int size = trueInstances.size() + falseInstances.size();
        // Calculate impurity
        double trueNode = calculateImpurity(trueInstances);
        double falseNode = calculateImpurity(falseInstances);

        // Calculate probability
        double falseProb = (falseInstances.size() + 0.0) / size;
        double trueProb = (trueInstances.size() + 0.0) / size;

        // Combine
        falseNode *= falseProb;
        trueNode *= trueProb;
        return trueNode + falseNode;
    }


    // Static util methods below

    /**
     * Calculate the impurity of a specified data set.
     * @param patients
     * @return
     */
    private static double calculateImpurity(List<Patient> patients){
        int live = countSpecificOutcome("live", patients);
        int die = patients.size() - live;

        return (live * die) / (Math.pow(live+die, 2));
    }


    /**
     * Do all patients contains the same outcome?
     * @param patients
     * @return
     */
    protected static boolean areInstancesPure(List<Patient> patients) {
        if (patients.isEmpty())
            return true;

        String outcome = patients.get(0).getOutcome();
        for (Patient patient : patients) {
            if(!patient.getOutcome().equals(outcome))
                return false;
        }
        return true;
    }


    /**
     * Count how many patients resulted in the outcome specified.
     * @param outcome
     * @param data
     * @return
     */
    private static int countSpecificOutcome(String outcome, List<Patient> data){
        int count = 0;
        for(Patient p : data){
            if(p.getOutcome().equals(outcome))
                count++;
        }
        return count;
    }
}
