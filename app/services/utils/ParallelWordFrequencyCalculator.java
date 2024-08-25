package services.utils;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ParallelWordFrequencyCalculator implements WordFrequencyCalculator {
    private HashMap<String, Integer> wordFrequency = new HashMap<>();
    @Override
    public void addString(String str) {
        WordSpliterator wordSpliterator = new WordSpliterator(str + " ");
        Stream<Character> wordStream = StreamSupport.stream(wordSpliterator, true);
        StreamWordCounter counter = wordStream
                .parallel()
                .collect(() -> new StreamWordCounter(true, "", new HashMap<>()),
                StreamWordCounter::accumulate,
                StreamWordCounter::combine);
        wordFrequency.putAll(counter.getWordFrequency());
    }

    @Override
    public HashMap<String, Integer> getWordFrequency() {
        return wordFrequency;
    }
}
