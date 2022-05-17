package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.util.ArrayList;

public class ProbabilityMassFunction implements Writable {
    private ArrayList<ProbabilityPair> pmf;

    public ProbabilityMassFunction() {
        pmf = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created new pmf chart"));
    }

    public ArrayList<ProbabilityPair> getPmf() {
        return pmf;
    }

    //MODIFIES: this
    //EFFECTS: adds a probability pair to pmf
    //         if the x value is already in the pmf, nothing will happen
    public void addProbabilityPair(Double x, Double y) {
        boolean isInPmf = false;
        for (ProbabilityPair pair : pmf) {
            if (Double.compare(pair.getXvalue(), x) == 0) {
                isInPmf = true;
                break;
            }
        }

        if (!isInPmf) {
            pmf.add(new ProbabilityPair(x, y));
            EventLog.getInstance().logEvent(new Event("added new pair: f(" + x + ") = " + y));
        }
    }

    //REQUIRES: x is not an xvalue already in pmf
    //EFFECTS: returns the corresponding output for the given x value according to the pmf
    //         if the given x value is not in the pmf, return 0 (that's how pmfs are defined in stats)
    public Double getProbability(Double x) {
        for (ProbabilityPair pair : pmf) {
            if (Double.compare(pair.getXvalue(), x) == 0) {
                return pair.getYvalue();
            }
        }
        return 0.0;
    }

    //MODIFIES: this
    //EFFECTS: normalize all the y values so that the probabilities add to 1
    public void normalizePmf() {
        Double sum = 0.0;
        for (ProbabilityPair pair : pmf) {
            sum += pair.getYvalue();
        }

        for (ProbabilityPair pair : pmf) {
            Double newY = pair.getYvalue() / sum;
            pair.setYvalue(newY);
        }
    }

    //REQUIRES: there is at least one value in pmf
    //EFFECTS: generates random x value with each x value having the probability of its corresponding y value
    public Double generateRandomValue(int n, int j) {
        normalizePmf();
        Random rand = new Random();
        double randNum = rand.nextDouble();

        Double sum = 0.0;

        for (int i = 0; i < pmf.size() - 1; i++) {
            ProbabilityPair pair = pmf.get(i);
            sum += pair.getYvalue();
            if (Double.compare(randNum, sum) < 0) {
                if (j == n - 1) {
                    EventLog.getInstance().logEvent(new Event("Generated " + n + " random values"));
                }
                return pair.getXvalue();
            }
        }

        //We are omitting the last probability pair in pmf so that the probability of returning something is 1
        if (j == n - 1) {
            EventLog.getInstance().logEvent(new Event("Generated " + n + " random values"));
        }
        return pmf.get(pmf.size() - 1).getXvalue();
    }

    // MODIFIES: this
    // EFFECTS: removes the element at i index in pmf
    public void delete(int i) {
        pmf.remove(i);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pmf", probabilityPairsToJson());
        return json;
    }

    // EFFECTS: returns probabilityPairs in this pmf as a JSON array
    private JSONArray probabilityPairsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ProbabilityPair pair : pmf) {
            jsonArray.put(pair.toJson());
        }

        return jsonArray;
    }
}
