package ui;

import model.ProbabilityMassFunction;
import model.ProbabilityPair;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PmfApp {
    private static final String JSON_STORE = "./data/savedPmfs.json";
    Scanner console;
    Boolean isStarted;
    ProbabilityMassFunction pmf;
    int option;
    ArrayList<Double> dataset;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public PmfApp() throws FileNotFoundException {
        //initialize readers and writers
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        console = new Scanner(System.in);
        isStarted = false;
        pmf = new ProbabilityMassFunction();
        System.out.println("Please select option 1 to start");
        startApp();
    }

    // MODIFIES: pmf, isStarted
    // EFFECTS: runs entire app
    private void startApp() {
        while (true) {
            option = chooseOptions();
            handleOption(option);
            if (option == 6) {
                break;
            }
        }
    }

    // MODIFIES: pmf
    // EFFECTS: receives the users choice of action and moves to the next part of the application based on the choice
    private void handleOption(int option) {
        switch (option) {
            case (1):
                addPair();
                break;
            case (2):
                deletePair();
                break;
            case (3):
                generateRandomNumbers();
                break;
            case (4):
                savePmf();
                break;
            case (5):
                loadPmf();
                break;
            case (6):
                System.out.println("You are quitting");
                break;
        }
    }

    // MODIFIES: pmf
    // EFFECTS: Prompts the user for x,y value pairs and adds them as a probability pair to pmf
    private void addPair() {
        System.out.println("Your pmf will automatically be normalized so that the total probability equals 1");
        System.out.println("Type decimal numbers in a pair to make f(x) = y probability pairs");

        System.out.print("type the x value: ");
        Double xvalue = console.nextDouble();
        System.out.print("type the y value (probability/odd of getting the x value): ");
        Double yvalue = console.nextDouble();
        pmf.addProbabilityPair(xvalue, yvalue);
        System.out.println("Done!");
    }

    // MODIFIES: pmf
    // EFFECTS: prompts the user to choose which pair to delete and deletes it from pmf
    private void deletePair() {
        int index;

        if (pmf.getPmf().size() == 0) {
            System.out.println("There is nothing to delete :(");
            return;
        }

        while (true) {
            System.out.println("You are removing a probability pair.");
            System.out.println("There are " + pmf.getPmf().size() + " probability pairs.");
            System.out.println("Which pair do you want to remove?");
            System.out.println("(enter a number from 1 to " + pmf.getPmf().size() + ")");
            index = console.nextInt();
            if (index >= 1 && index < pmf.getPmf().size() + 1) {
                break;
            }
            System.out.println("There is no such pmf please try again");
            System.out.println("(enter a number from 1 to " + pmf.getPmf().size() + ")");
        }

        pmf.delete(index - 1);
        System.out.println("You have deleted the pair at position " + index);
        System.out.println("Your new pmf is: ");
        for (ProbabilityPair pair : pmf.getPmf()) {
            System.out.println("f(" + pair.getXvalue() + ") = " + pair.getYvalue());
        }
    }

    // EFFECTS: prompts the user for the amount of numbers to generate and
    //          generates an array list of random numbers with numNumbers in size and according to the probability
    //          distribution in the pmf and prints out the numbers
    private void generateRandomNumbers() {
        if (pmf.getPmf().size() == 0) {
            System.out.println("There is nothing to generate yet :(");
            return;
        }
        int numNumbers;
        int pmfIndex;
        System.out.println("How many numbers do you want to generate?");
        numNumbers = console.nextInt();

        dataset = generateRandomNumberList(numNumbers);
        System.out.println("Here are the generated numbers: ");
        for (Double number : dataset) {
            System.out.println(number);
        }
    }

    // EFFECTS: generates an array list of random numbers with numNumbers in size and according to the probability
    //          distribution in the pmf; returns the arraylist
    private ArrayList<Double> generateRandomNumberList(int numNumbers) {
        ArrayList<Double> numbers = new ArrayList<>();
        for (int i = 0; i < numNumbers; i++) {
            numbers.add(pmf.generateRandomValue(1,0));
        }
        return numbers;
    }

    //EFFECTS: Prompts user to choose options to do and returns the user input
    //         1 means make a new pmf, 2 means delete a pmf, 3 means generate random numbers, 4 means save pmf,
    //         5 means load pmf and 6 means quit
    private int chooseOptions() {
        System.out.println("What do you want to do?");
        System.out.println("1 -> Add a probability pair to the pmf");
        System.out.println("2 -> Delete a probability pair");
        System.out.println("3 -> Generate random numbers");
        System.out.println("4 -> Save pmf");
        System.out.println("5 -> Load pmf");
        System.out.println("6 -> Quit");
        return console.nextInt();
    }



    // This method is modeled from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: writes pmf to file
    private void savePmf() {
        try {
            jsonWriter.open();
            jsonWriter.write(pmf);
            jsonWriter.close();
            System.out.println("Saved pmfs to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // This method is modeled from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads pmf from file
    private void loadPmf() {
        try {
            pmf = jsonReader.read();
            System.out.println("Loaded Probability Mass Functions from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}