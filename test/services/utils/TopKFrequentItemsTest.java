package services.utils;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TopKFrequentItemsTest {

    @Test
    public void testTopKFrequentItems() {
        TopKFrequentItems<String> topKFrequentItems = new TopKFrequentItems<>(2);
        topKFrequentItems.add("a");
        topKFrequentItems.add("b");
        topKFrequentItems.add("a");
        topKFrequentItems.add("c");
        
        assertEquals(2, topKFrequentItems.getTopK().size());
        final String[] expected = {"a", "c"};
        assertArrayEquals(expected, topKFrequentItems.getTopK().toArray());
    }
}