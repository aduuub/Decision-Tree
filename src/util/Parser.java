package util;

import main.Patient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Adam on 24/03/17.
 */
public class Parser {

    private int numCategories;
    private List<String> categoryNames;
    private List<String> attNames;
    private List<Patient> patients;

    /**
     * Create a Parser and start reading in the specified data file
     * @param fileName
     */
    public Parser(String fileName) {
        readDataFile(fileName);
    }


    /**
     * Reads the data from the file
     * @param fileName
     */
    private void readDataFile(String fileName) {
        System.out.println("Reading data from file " + fileName);
        try {
            Scanner din = new Scanner(new File(fileName));

            categoryNames = new ArrayList<>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext(); )
                categoryNames.add(s.next());

            numCategories = categoryNames.size();
            System.out.println(numCategories + " categories");

            attNames = new ArrayList<>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext(); )
                attNames.add(s.next());

            patients = readPatients(din);
            din.close();
        } catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }


    /**
     * Read the patients values from the scanner
     * @param din
     * @return
     */
    private List<Patient> readPatients(Scanner din) {
        List<Patient> patients = new ArrayList<>();
        while (din.hasNext()) {
            Scanner line = new Scanner(din.nextLine());
            patients.add(new Patient(categoryNames.indexOf(line.next()), line));
        }
        System.out.println("Read " + patients.size() + " patients");
        return patients;
    }


    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public List<String> getAttNames() {
        return attNames;
    }

    public List<Patient> getPatients() {
        return patients;
    }

}
