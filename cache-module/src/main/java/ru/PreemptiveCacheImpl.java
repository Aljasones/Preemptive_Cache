package ru;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PreemptiveCacheImpl<K, V> implements PreemptiveCache<K, V> {
    private Map<K, CacheEntry<V>> cache;
    private Queue<K> keyQueue;
    private int maxSize;
    private AtomicInteger cacheSize = new AtomicInteger();

    public PreemptiveCacheImpl(int maxSize) {
        this.maxSize = maxSize;
        cache = new ConcurrentHashMap<K, CacheEntry<V>>(maxSize);
        keyQueue = new ConcurrentLinkedQueue<K>();
    }

    private class CacheEntry<V> {
        private V entry;

        public CacheEntry(V entry) {
            this.entry = entry;
        }

        public V getEntry() {
            return entry;
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        boolean exists = cache.containsKey(key);
        if (!exists) {
            cacheSize.incrementAndGet();
            while (cacheSize.get() > maxSize) {
                remove(keyQueue.poll());
            }
        }
        cache.put(key, new CacheEntry<V>(value));
        keyQueue.add(key);
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        CacheEntry<V> cacheEntry = cache.get(key);
        if (cacheEntry == null) {
            return (V) Optional.empty();
        }
        return cacheEntry.getEntry();
    }

    public List getAllValue() {
        List result = new LinkedList();
        for (Map.Entry entry : cache.entrySet()) {
            CacheEntry<V> cacheEntry = cache.get(entry.getKey());
            result.add(cacheEntry.getEntry());
        }
        return result;
    }

    public Map getAll() {
        Map result = new HashMap();
        for (Map.Entry entry : cache.entrySet()) {
            CacheEntry<V> cacheEntry = cache.get(entry.getKey());
            result.put(entry.getKey(), cacheEntry.getEntry());
        }
        return result;
    }

    public V removeAndGet(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return (V) Optional.empty();
        }
        cacheSize.decrementAndGet();
        return cache.remove(key).getEntry();
    }

    public boolean remove(K key) {
        return removeAndGet(key) != null;
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
        keyQueue.clear();
    }
}

