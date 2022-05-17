package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ProbabilityPairTest {
    @Test
    void toStringTest() {
        ProbabilityPair pair = new ProbabilityPair(1.0, 2.0);
        ProbabilityPair pair2 = new ProbabilityPair(1.5, 0.2);
        assertEquals("1.0: 2.0", pair.toString());
        assertEquals("1.5: 0.2", pair2.toString());
    }
}
