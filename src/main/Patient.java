package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The patient represents an instance of data and contains a list of attributes and an outcome of that patient
 */
public class Patient {

    private int outcome; // Live = 0, Die = 1
    private List<Boolean> attributes; // Values for the attributes


    public Patient(int cat, Scanner s) {
        outcome = cat;
        attributes = new ArrayList<>();
        while (s.hasNextBoolean())
            attributes.add(s.nextBoolean());
    }

    /**
     * Get a specific attribute at the index specified.
     * @param index
     * @return
     */
    public boolean getAttribute(int index) {
        return attributes.get(index);
    }

    /**
     * Get the outcome (result) of the patient
     * @return
     */
    public int getOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("Category: " + outcome);
        ans.append(" ");
        for (Boolean val : attributes)
            ans.append(val ? "true  " : "false ");
        return ans.toString();
    }
}