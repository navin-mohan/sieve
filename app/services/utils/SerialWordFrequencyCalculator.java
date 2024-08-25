package services.utils;

import java.util.HashMap;
import java.util.stream.Stream;

public class SerialWordFrequencyCalculator implements WordFrequencyCalculator {

    protected HashMap<String, Integer> wordFrequency = new HashMap<>();
    @Override
    public void addString(String str) {
        Stream<Character> stream = (str + " ").chars().mapToObj(c -> (char) c);
        StreamWordCounter streamWordCounter = stream.collect(() -> new StreamWordCounter(true, "", new HashMap<>()),
                StreamWordCounter::accumulate,
                StreamWordCounter::combine);
        wordFrequency = streamWordCounter.getWordFrequency();
    }

    @Override
    public HashMap<String, Integer> getWordFrequency() {
        return wordFrequency;
    }
}
