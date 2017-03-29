package util;

import main.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 24/03/17.
 */
public class SubListBuilder {
    private List<Patient> trueAttributes;
    private List<Patient> falseAttributes;

    /**
     * Splits the patients into two sub-lists (one true, one false) depending on the patients value for the attribute
     * @param patients
     * @param attributeIndex
     */
    public SubListBuilder(List<Patient> patients, int attributeIndex) {
        trueAttributes = new ArrayList<>();
        falseAttributes = new ArrayList<>();

        for (Patient patient : patients) {
            boolean val = patient.getAttribute(attributeIndex);
            if (val)
                trueAttributes.add(patient);
            else
                falseAttributes.add(patient);
        }
    }

    public List<Patient> getTrueAttributes() {
        return trueAttributes;
    }

    public List<Patient> getFalseAttributes() {
        return falseAttributes;
    }
}
