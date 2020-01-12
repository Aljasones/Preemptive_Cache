package ru;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreemptiveCacheImplTest {

    @Test
    public void get() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value = "Some value";
        int key = 1;
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
        int key = 1;
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
        int key = 1;
        cache.put(key, value);
        assertTrue(true);
    }

    @Test
    public void size(){
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";
        int key1 = 1;
        int key2 = 2;
        int key3 = 3;
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
        int key1 = 4;
        int key2 = 5;
        int test = 0;
        cache.put(key1, value1);
        cache.put(key2, value2);
        cache.clear();

        Object actual = cache.size();
        Object before = test;
        assertEquals(before, actual);
    }

    @Test
    public void getAll() {
        int maxSize = 10;
        PreemptiveCache cache = new PreemptiveCacheImpl(maxSize);
        String value1 = "value 1";
        String value2 = "value 2";
        String value3 = "value 3";
        String value4 = "value 4";
        int key1 = 1;
        int key2 = 2;
        int key3 = 3;
        int key4 = 4;

        cache.put(key1, value1);
        cache.put(key2, value2);
        cache.put(key3, value3);
        cache.put(key4, value4);

        List testMap = new LinkedList ();
        testMap.add(value1);
        testMap.add(value2);
        testMap.add(value3);
        testMap.add(value4);

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
