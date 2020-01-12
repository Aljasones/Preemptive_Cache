package ru;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PreemptiveCache<K, V> {

     V get(K key);

     List getAll();

     V removeAndGet(K key);

     void put(K key, V value);

     boolean remove(K key);

     int size();

     void clear();

}
