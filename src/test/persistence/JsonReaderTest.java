package persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.List;
import model.ProbabilityMassFunction;
import model.ProbabilityPair;
import model.Writable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

// This class is modeled from JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistingFile123.json");
        try {
            ProbabilityMassFunction pmf = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected behaviour
        }
    }

    @Test
    void testReaderEmptyProbabilityMassFunction() {
        JsonReader reader = new JsonReader("./data/testJsonReaderEmptyPmfData.json");
        try {
            ProbabilityMassFunction pmf = reader.read();
            assertEquals(0, pmf.getPmf().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testJsonReaderGenericPmfData.json");
        try {
            ProbabilityMassFunction pmf = reader.read();
            assertEquals(3, pmf.getPmf().size());
            ArrayList<ProbabilityPair> myPmf = pmf.getPmf();
            assertEquals(1.0, myPmf.get(0).getXvalue());
            assertEquals(2.0, myPmf.get(1).getXvalue());
            assertEquals(3.0, myPmf.get(2).getXvalue());
            assertEquals(0.5, myPmf.get(0).getYvalue());
            assertEquals(0.25, myPmf.get(1).getYvalue());
            assertEquals(0.25, myPmf.get(2).getYvalue());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
