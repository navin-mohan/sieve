package services.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class ParallelWordFrequencyCalculatorTest extends TestCase {
    @Test
    public void testFrequencyCalculator() {
        WordFrequencyCalculator wordFrequencyCalculator = new ParallelWordFrequencyCalculator();
        wordFrequencyCalculator.addString("a b a c,a a b a c,a a b a c,a a b a c,a a b a c,a");
        assertEquals(15, wordFrequencyCalculator.getWordFrequency().get("a").intValue());
        assertEquals(5, wordFrequencyCalculator.getWordFrequency().get("b").intValue());
        assertEquals(5, wordFrequencyCalculator.getWordFrequency().get("c").intValue());
    }
}