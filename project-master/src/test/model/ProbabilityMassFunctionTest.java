package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProbabilityMassFunctionTest {
    ProbabilityMassFunction pmf;

    @BeforeEach
    void Before() {
        pmf = new ProbabilityMassFunction();
    }

    @Test
    void addProbabilityPairTest() {
        pmf.addProbabilityPair(1.0,2.0);
        assertEquals(1, pmf.getPmf().size());
        pmf.addProbabilityPair(2.0,3.0);
        assertEquals(2, pmf.getPmf().size());
        pmf.addProbabilityPair(2.0, 4.0);
        assertEquals(2, pmf.getPmf().size());
    }

    @Test
    void getProbabilityTest() {
        pmf.addProbabilityPair(1.0,0.3);
        pmf.addProbabilityPair(2.0, 0.7);
        assertEquals(0.7, pmf.getProbability(2.0));
        assertEquals(0.3, pmf.getProbability(1.0));
        assertEquals(0.0, pmf.getProbability(29.35));
    }

    @Test
    void normalizePmfTest() {
        pmf.addProbabilityPair(1.0,2.0);
        pmf.addProbabilityPair(2.0, 8.0);
        pmf.normalizePmf();
        assertEquals(0.2, pmf.getPmf().get(0).getYvalue());
        assertEquals(0.8, pmf.getPmf().get(1).getYvalue());
    }

    @Test
    void deleteTest() {
        pmf.addProbabilityPair(1.0,0.3);
        pmf.addProbabilityPair(2.0, 0.7);
        pmf.delete(0);
        assertEquals(1, pmf.getPmf().size());
        assertEquals(2.0, pmf.generateRandomValue(1,0));
    }

    @Test
    void generateRandomValueTest() {

        Double testValue1;
        Double testValue2;
        Double testValue3;
        Double testValue4;

        pmf.addProbabilityPair(1.0,1.0);
        testValue1 = pmf.generateRandomValue(1,0);
        assertEquals(testValue1, 1.0);

        pmf.addProbabilityPair(2.0, 2.0);
        pmf.addProbabilityPair(6.0,3.0);
        pmf.addProbabilityPair(7.0, 4.0);

        testValue2 = pmf.generateRandomValue(1,0);
        testValue3 = pmf.generateRandomValue(1,0);
        testValue4 = pmf.generateRandomValue(1,0);

        //checking to see that pmf contains all the generated values by trying to add the values back in
        //if the value is in the pmf, it cannot be added so the size stays the same
        pmf.addProbabilityPair(testValue1, 10.3);
        assertEquals(4, pmf.getPmf().size());
        pmf.addProbabilityPair(testValue2, 6.4);
        assertEquals(4, pmf.getPmf().size());
        pmf.addProbabilityPair(testValue3, 2.33);
        assertEquals(4, pmf.getPmf().size());
        pmf.addProbabilityPair(testValue4, 2.222);
        assertEquals(4, pmf.getPmf().size());
    }
}