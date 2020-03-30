package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    WeakHashMap<K, V> cacheHashMap = new WeakHashMap<K, V>();
    ArrayList<HwListener> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        for (HwListener listener :
                listeners) {
            listener.notify(key, value, "PPPput");
        }
        cacheHashMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        for (HwListener listener :
                listeners) {
            listener.notify(key, cacheHashMap.get(key), "RRRremove");
        }
        cacheHashMap.remove(key);
    }

    @Override
    public V get(K key) {
        return (V) cacheHashMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
