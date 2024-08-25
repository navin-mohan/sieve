package services.utils;

import java.util.*;
import java.util.stream.Collectors;

public class TopKFrequentItems<T extends Comparable<T>> {
    private class FrequencyElementPair implements Comparable<FrequencyElementPair> {
        private int frequency;
        private T item;

        public FrequencyElementPair(int frequency, T item) {
            this.frequency = frequency;
            this.item = item;
        }

        public int frequency() {
            return frequency;
        }

        public T item() {
            return item;
        }

        @Override
        public int compareTo(FrequencyElementPair o) {
            if (frequency != o.frequency) {
                return Integer.compare(frequency, o.frequency);
            }
            return item.compareTo(o.item);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            FrequencyElementPair p = (FrequencyElementPair) obj;
            return p.frequency == frequency && p.item.equals(item);
        }
    }
    private HashMap<T, Integer> frequencyMap = new HashMap<>();
    private SortedSet<FrequencyElementPair> topKItems = new TreeSet<>();

    private final int k;

    public TopKFrequentItems(int k) {
        this.k = k;
    }

    public void add(T item, int frequency) {
        final int itemFrequency = frequencyMap.getOrDefault(item, 0);

        if (itemFrequency != 0) {
            topKItems.remove(new FrequencyElementPair(itemFrequency, item));
        }

        final int newFrequency = itemFrequency + frequency;

        frequencyMap.put(item, newFrequency);

        if (topKItems.size() < k) {
            topKItems.add(new FrequencyElementPair(newFrequency, item));
            return;
        }

        if (topKItems.first().frequency() <= newFrequency) {
            topKItems.remove(topKItems.first());
            topKItems.add(new FrequencyElementPair(newFrequency, item));
        }
    }

    public List<T> getTopK() {
        return topKItems.stream()
                .map(FrequencyElementPair::item)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> { Collections.reverse(list); return list; }));
    }
}
