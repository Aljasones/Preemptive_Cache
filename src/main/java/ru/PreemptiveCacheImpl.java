package ru;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PreemptiveCacheImpl<K, V> implements PreemptiveCache<K, V>{
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

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }

        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return null;
        }

        return entry.getEntry();
    }

    public V removeAndGet(K key) {
        if (key == null) {
            return null;
        }

        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            cacheSize.decrementAndGet();
            return cache.remove(key).getEntry();
        }

        return null;
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

    public boolean remove(K key) {
        return removeAndGet(key) != null;
    }

    public int size() {
        return cache.size();
    }

    public Map<K, V> getAll(Collection<K> collection) {
        Map<K, V> resultMap = new HashMap<K, V>();
        for (K o : collection) {
            resultMap.put(o, get(o));
        }
        return resultMap;
    }

    public void clear() {
        cache.clear();
        keyQueue.clear();
    }
}

