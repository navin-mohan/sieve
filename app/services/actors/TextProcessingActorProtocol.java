package services.actors;


import services.utils.WordFrequencyCalculator;

import java.util.HashMap;

public class TextProcessingActorProtocol {
    public static class GetWordFrequency {
        private final String text;
        private final boolean isParallel;

        public GetWordFrequency(String text, boolean isParallel) {
            this.text = text;
            this.isParallel = isParallel;
        }

        public String getText() {
            return text;
        }

        public boolean isParallel() {
            return isParallel;
        }

        public HashMap<String, Integer> process() {
            WordFrequencyCalculator wordFrequencyCalculator = isParallel() ? new services.utils.ParallelWordFrequencyCalculator() : new services.utils.SerialWordFrequencyCalculator();

            wordFrequencyCalculator.addString(text);

            return wordFrequencyCalculator.getWordFrequency();
        }
    }
}
