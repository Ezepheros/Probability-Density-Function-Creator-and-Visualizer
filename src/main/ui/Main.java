package ui;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.ProbabilityMassFunction;

import javax.sound.midi.Soundbank;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            new PmfApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
