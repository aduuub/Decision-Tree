package util;

import main.Patient;

import java.util.List;

/**
 * Created by Adam on 24/03/17.
 */
public class NodePurityCalculator {

    private int attribute;
    private List<Patient> trueInstances, falseInstances;

    //private double calculatedProbabilty;

    public NodePurityCalculator(int attribute, List<Patient> truePatients, List<Patient> falsePatients) {
        this.attribute = attribute;
        this.trueInstances = truePatients;
        this.falseInstances = falsePatients;
    }


    public double calculateImpurity(){
        int m = trueInstances.size();
        int n = falseInstances.size();

        return (m * n) / (Math.pow(m+n, 2));
    }


    public static boolean areInstancesPure(List<Patient> patients) {
        if (patients.isEmpty())
            return true;

        int category = patients.get(0).getCategory();
        for (Patient patient : patients) {
            if(patient.getCategory() != category)
                return false;
        }

        return true;
    }
}
