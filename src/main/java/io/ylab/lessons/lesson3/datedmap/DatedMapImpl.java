package io.ylab.lessons.lesson3.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DatedMapImpl implements DatedMap {

    private Map<String, Date> datedMap;
    private Map<String, String> currentMap;

    public DatedMapImpl() {
        datedMap = new HashMap<>();
        currentMap = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        currentMap.put(key, value);
        datedMap.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return currentMap.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return currentMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        if (currentMap.containsKey(key)) {
            currentMap.remove(key);
        }
        if (datedMap.containsKey(key)) {
            datedMap.remove(key);
        }
    }

    @Override
    public Set<String> keySet() {
        return datedMap.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return datedMap.get(key);
    }
}
