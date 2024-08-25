package services.utils;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordSpliterator implements Spliterator<Character> {

    private String str;
    private int currentChar = 0;
    private static final int THRESHOLD = 50;

    public WordSpliterator(String str) {
        this.str = str;
    }

    public static boolean isDelimiter(char c) {
        return Character.isWhitespace(c) || c == '.' || c == ',' || c == ';' || c == ':';
    }


    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(str.charAt(currentChar++));
        return currentChar < str.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        final int currentSize = str.length() - currentChar;
        if (currentSize < THRESHOLD) {
            return null;
        }
        for (int splitPos = currentSize / 2 + currentChar; splitPos < str.length(); splitPos++) {
            if (isDelimiter(str.charAt(splitPos))) {
                Spliterator<Character> spliterator =  new WordSpliterator(str.substring(currentChar, splitPos + 1));
                currentChar = splitPos + 1;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return str.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
