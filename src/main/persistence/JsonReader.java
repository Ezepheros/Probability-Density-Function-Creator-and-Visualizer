package persistence;

import model.Event;
import model.EventLog;
import model.ProbabilityPair;
import model.ProbabilityMassFunction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// This class is modeled from JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads ProbabilityMassFunction from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads probability mass function from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ProbabilityMassFunction read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded pmf chart"));
        return parsePmf(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses pmf from JSON object and returns it
    private ProbabilityMassFunction parsePmf(JSONObject jsonObject) {
        ProbabilityMassFunction pmf = new ProbabilityMassFunction();
        addPairs(pmf, jsonObject);
        return pmf;
    }



    // MODIFIES: pmf
    // EFFECTS: parses probability pairs from JSON object and adds it to pmf
    private void addPairs(ProbabilityMassFunction pmf, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pmf");

        for (Object json : jsonArray) {
            JSONObject nextPair = (JSONObject) json;
            addPair(pmf, nextPair);
        }
    }

    // MODIFIES: pmf
    // EFFECTS: parses a probability pair and adds it to pmf
    private void addPair(ProbabilityMassFunction pmf, JSONObject jsonObject) {
        Double xvalue = Double.valueOf(jsonObject.getString("xvalue"));
        Double yvalue = Double.valueOf(jsonObject.getString("yvalue"));
        pmf.addProbabilityPair(xvalue, yvalue);
    }
}

