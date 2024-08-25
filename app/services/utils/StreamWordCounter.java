package services.utils;

import java.util.HashMap;

public class StreamWordCounter {
    private boolean lastSpace;
    private String word;
    private HashMap<String, Integer> wordFrequency;
    public StreamWordCounter(boolean lastSpace, String word, HashMap<String, Integer> wordFrequency) {
        this.lastSpace = lastSpace;
        this.word = word;
        this.wordFrequency = wordFrequency;

    }
    public void accumulate(Character c) {
        if (lastSpace) {
            if (!WordSpliterator.isDelimiter(c)) {
                this.lastSpace = false;
                this.word += c;
            }
            return;
        }

        if (WordSpliterator.isDelimiter(c)) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            this.lastSpace = true;
            this.word = "";
        } else {
            this.lastSpace = false;
            this.word += c;
        }
    }
    public void combine(StreamWordCounter wordCounter) {
        HashMap<String, Integer> mergedWordFrequency = new HashMap<>();
        wordFrequency.forEach(
                (word, frequency) -> mergedWordFrequency.put(word, mergedWordFrequency.getOrDefault(word, 0) + frequency));
        wordCounter.wordFrequency.forEach(
                (word, frequency) -> mergedWordFrequency.put(word, mergedWordFrequency.getOrDefault(word, 0) + frequency));

        wordFrequency = mergedWordFrequency;
    }

    public HashMap<String, Integer> getWordFrequency() {
        return wordFrequency;
    }
}
