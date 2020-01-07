package ru;

import java.util.Collection;
import java.util.Map;

public interface PreemptiveCache<K, V> {

    V get(K key);

    V removeAndGet(K key);

    void put(K key, V value, int secondsToLive);

    boolean remove(K key);

    int size();

    Map<K, V> getAll(Collection<K> collection);

}
