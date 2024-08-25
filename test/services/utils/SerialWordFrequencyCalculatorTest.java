package services.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class SerialWordFrequencyCalculatorTest extends TestCase {
    @Test
    public void testFrequencyCalculator() {
        WordFrequencyCalculator wordFrequencyCalculator = new SerialWordFrequencyCalculator();
        wordFrequencyCalculator.addString("a b a c,a");
        assertEquals(3, wordFrequencyCalculator.getWordFrequency().get("a").intValue());
        assertEquals(1, wordFrequencyCalculator.getWordFrequency().get("b").intValue());
        assertEquals(1, wordFrequencyCalculator.getWordFrequency().get("c").intValue());
    }


}