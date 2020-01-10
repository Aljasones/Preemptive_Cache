package ru;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreemptiveCacheImplTest {
    PreemptiveCacheImpl cache = new PreemptiveCacheImpl(10);

    @Test
    void get() {
        String value = "12345";
        int key = 1;
        cache.put(key, value);
        assertEquals(value, cache.get(key));
    }

    @Test
    void removeAndGet() {
        String value = "Some value...";
        int key = 2;
        cache.put(key, value);
        assertEquals(value, cache.removeAndGet(key));
    }

    @Test
    void remove () {
        String value = "Some value for remove";
        int key = 3;
        boolean trueForTest = true;
        cache.put(key, value);
        assertEquals(trueForTest, cache.remove(key));
    }
//    @Test
//    void clear() {
//        String value1 = "value 1";
//        String value2 = "value 2";
//        int key = 4;
//        int keyMore = 5;
//        cache.put(key, value1);
//        cache.put(keyMore, value2);
//        assertEquals( cache.clear());
//    }

}
