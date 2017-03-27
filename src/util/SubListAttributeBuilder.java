package util;

import main.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 24/03/17.
 */
public class SubListAttributeBuilder {
    private List<Patient> trueAttributes;
    private List<Patient> falseAttributes;


    public SubListAttributeBuilder(List<Patient> patients, int attributeIndex) {

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
