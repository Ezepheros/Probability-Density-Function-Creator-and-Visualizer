package model;

import org.json.JSONObject;

// this class represents the output (yvalue) of the probability mass function given the input (xvalue)
// mathematically looks like this: f(x) = y , where f(x) is the probability mass function
public class ProbabilityPair implements Writable {
    private Double xvalue;
    private Double yvalue;

    public ProbabilityPair(Double x, Double y) {
        xvalue = x;
        yvalue = y;
    }

    public Double getXvalue() {
        return xvalue;
    }

    public Double getYvalue() {
        return yvalue;
    }

    public void setYvalue(Double y) {
        yvalue = y;
    }

    // EFFECTS: returns string representation of this Probability Pair
    public String toString() {
        return xvalue + ": " + yvalue;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xvalue", xvalue.toString());
        json.put("yvalue", yvalue.toString());
        return json;
    }
}
