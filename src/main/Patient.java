package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient {

    private int category; // Live = 0, Die = 1
    private List<Boolean> values; // Values for the attributes

    public Patient(int cat, Scanner s) {
        category = cat;
        values = new ArrayList<>();
        while (s.hasNextBoolean())
            values.add(s.nextBoolean());
    }

    public boolean getAttribute(int index) {
        return values.get(index);
    }

    public int getCategory() {
        return category;
    }

    public String toString() {
        StringBuilder ans = new StringBuilder("Category: " + category);
        ans.append(" ");
        for (Boolean val : values)
            ans.append(val ? "true  " : "false ");
        return ans.toString();
    }
}