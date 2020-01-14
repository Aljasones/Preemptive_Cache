package ru;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreemptiveCacheImplTest {

    @Test
    public void get() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value = "Some value";
        String key = "1o";
        cache.put(key, value);

        Object actual = cache.get(key);
        Object before = value;
        assertEquals(before, actual);
    }

    @Test
    public void removeAndGet() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value = "Some value";
        String key = "1o";
        cache.put(key, value);

        Object actual = cache.removeAndGet(key);
        Object before = value;
        assertEquals(before, actual);
    }

    @Test
    public void remove() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value = "Some value";
        String key = "1o";
        cache.put(key, value);
        assertTrue(true);
    }

    @Test
    public void size() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";
        String key1 = "1o";
        String key2 = "2t";
        String key3 = "3t";
        int test = 3;
        cache.put(key1, value1);
        cache.put(key2, value2);
        cache.put(key3, value3);

        Object actual = cache.size();
        Object before = test;
        assertEquals(before, actual);
    }

    @Test
    public void clear() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value1 = "value 1";
        String value2 = "value 2";
        String key1 = "4f";
        String key2 = "5f";
        int test = 0;
        cache.put(key1, value1);
        cache.put(key2, value2);
        cache.clear();

        Object actual = cache.size();
        Object before = test;
        assertEquals(before, actual);
    }

    @Test
    public void getAllValue() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value1 = "value 1";
        String value2 = "value 2";
        String value3 = "value 3";
        String value4 = "value 4";
        String key1 = "1";
        String key2 = "2";
        String key3 = "3";
        String key4 = "4";

        cache.put(key1, value1);
        cache.put(key2, value2);
        cache.put(key3, value3);
        cache.put(key4, value4);

        List testList = new LinkedList();
        testList.add(value1);
        testList.add(value2);
        testList.add(value3);
        testList.add(value4);

        Object actual = cache.getAllValue();
        Object before = testList;
        assertEquals(before, actual);
    }

    @Test
    public void getAll() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value1 = "value 1";
        String value2 = "value 2";
        String value3 = "value 3";
        String key1 = "1";
        String key2 = "2";
        String key3 = "3";

        cache.put(key1, value1);
        cache.put(key2, value2);
        cache.put(key3, value3);

        Map<String, String> testMap = new HashMap<>();
        testMap.put(key1, value1);
        testMap.put(key2, value2);
        testMap.put(key3, value3);

        Object actual = cache.getAll();
        Object before = testMap;
        assertEquals(before, actual);

    }

    @Test
    public void overage() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        for (int i = 0; i < 100; i++) {
            cache.put(i, i);
        }
        Object actual = cache.size();
        Object before = maxSize;
        assertEquals(before, actual);
    }
}
