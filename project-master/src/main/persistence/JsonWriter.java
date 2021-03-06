package persistence;

import model.Event;
import model.EventLog;
import model.ProbabilityMassFunction;
import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;

// This class is modeled from JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes JSON representation of ProbabilityMassFunction to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ProbabilityMassFunction to file
    public void write(ProbabilityMassFunction pmf) {
        JSONObject json = pmf.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved pmf chart"));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
