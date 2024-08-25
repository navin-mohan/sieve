package services.utils;

import java.util.HashMap;
import java.util.StringTokenizer;

public class SerialWordFrequencyCalculator implements WordFrequencyCalculator {

    protected HashMap<String, Integer> wordFrequency = new HashMap<>();
    @Override
    public void addString(String str) {
        final StringTokenizer tokenizer = new StringTokenizer(str, " ,.:;\n\t");
        while (tokenizer.hasMoreTokens()) {
            final String word = tokenizer.nextToken();
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
    }

    @Override
    public HashMap<String, Integer> getWordFrequency() {
        return wordFrequency;
    }
}
