package ru;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PreemptiveCacheImpl<K, V> implements PreemptiveCache {
    private Map<K, CacheEntry<V>> cache;
    private Queue<K> keyQueue;
    private int maxSize;
    private AtomicInteger cacheSize = new AtomicInteger();

    public PreemptiveCacheImpl(int maxSize) {
        this.maxSize = maxSize;
        cache = new ConcurrentHashMap<K, CacheEntry<V>>(maxSize);
        keyQueue = new ConcurrentLinkedQueue<K>();
    }

    private class CacheEntry<T> {
        private long expireBy;
        private T entry;

        public CacheEntry(long expireBy, T entry) {
            super();
            this.expireBy = expireBy;
            this.entry = entry;
        }

        public long getExpireBy() {
            return expireBy;
        }

        public T getEntry() {
            return entry;
        }

    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key.");
        }

        CacheEntry<V> entry = cache.get(key);

        if (entry == null) {
            return null;
        }

        long timestamp = entry.getExpireBy();
        if (timestamp != -1 && System.currentTimeMillis() > timestamp) {
            remove(key);
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
    public void put(K key, V value, int secondsToLive) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Invalid Value.");
        }

        long expireBy = secondsToLive != -1 ? System.currentTimeMillis()
                + (secondsToLive * 1000) : secondsToLive;
        boolean exists = cache.containsKey(key);
        if (!exists) {
            cacheSize.incrementAndGet();
            while (cacheSize.get() > maxSize) {
                remove(keyQueue.poll());
            }
        }
        cache.put(key, new CacheEntry<V>(expireBy, value));
        keyQueue.add(key);
    }
    public boolean remove(K key) {
        return removeAndGet(key) != null;
    }

    public int size() {
        return cacheSize.get();
    }

    public Map<K, V> getAll(Collection<K> collection) {
        Map<K, V> ret = new HashMap<K, V>();
        for (K o : collection) {
            ret.put(o, get(o));
        }
        return ret;
    }

    public void clear() {
        cache.clear();
    }


}
