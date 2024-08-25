package services.actors;

import java.util.HashMap;

public class Top3LeastFrequentWordsActorProtocol {
    public static class GetWords { }
    public static class AddNewEntry {
        private final HashMap<String, Integer> wordFrequency;

        public AddNewEntry(HashMap<String, Integer> wordFrequency) {
            this.wordFrequency = wordFrequency;
        }

        public HashMap<String, Integer> getWordFrequency() {
            return wordFrequency;
        }
    }
}
