package services.utils;

import java.util.HashMap;

public interface WordFrequencyCalculator {
    void addString(String str);
    HashMap<String, Integer> getWordFrequency();
}
