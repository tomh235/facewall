package facewall.database.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ForwardingMap<K, V> implements Map<K, V> {

    private final Map<K, V> backingMap;

    protected ForwardingMap(Map<K, V> backingMap) {
        this.backingMap = backingMap;
    }

    @Override public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    @Override public boolean containsValue(Object value) {
        return backingMap.containsValue(value);
    }

    @Override public V get(Object key) {
        return backingMap.get(key);
    }

    @Override public V put(K key, V value) {
        return backingMap.put(key, value);
    }

    @Override public void putAll(Map<? extends K, ? extends V> map) {
        backingMap.putAll(map);
    }

    @Override public V remove(Object object) {
        return backingMap.remove(object);
    }

    @Override public boolean equals(Object object) {
        return object instanceof Map && backingMap.equals(object);
    }

    @Override public int hashCode() {
        return backingMap.hashCode();
    }

    @Override public String toString() {
        return backingMap.toString();
    }

    @Override public void clear() {
        backingMap.clear();
    }

    @Override public Set<K> keySet() {
        return backingMap.keySet();
    }

    @Override public Collection<V> values() {
        return backingMap.values();
    }

    @Override public Set<Entry<K, V>> entrySet() {
        return backingMap.entrySet();
    }

    @Override public int size() {
        return backingMap.size();
    }

    @Override public boolean isEmpty() {
        return backingMap.isEmpty();
    }
}
