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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class is modeled from JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ProbabilityMassFunction pmf = new ProbabilityMassFunction();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ProbabilityMassFunction pmf = new ProbabilityMassFunction();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPmf.json");
            writer.open();
            writer.write(pmf);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPmf.json");
            pmf = reader.read();
            assertEquals(0, pmf.getPmf().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ProbabilityMassFunction pmf = new ProbabilityMassFunction();
            pmf.addProbabilityPair(1.0, 2.0);
            pmf.addProbabilityPair(2.0, 3.0);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPmf.json");
            writer.open();
            writer.write(pmf);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPmf.json");
            pmf = reader.read();
            ArrayList<ProbabilityPair> myPmf = pmf.getPmf();
            assertEquals(2, myPmf.size());
            assertEquals(1.0, myPmf.get(0).getXvalue());
            assertEquals(2.0, myPmf.get(1).getXvalue());
            assertEquals(2.0, myPmf.get(0).getYvalue());
            assertEquals(3.0, myPmf.get(1).getYvalue());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
