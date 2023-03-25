package io.ylab.lessons.lesson3.datedmap;

import java.util.concurrent.TimeUnit;

public class DatedMapTest {
    public static void main(String[] args) throws InterruptedException {
        DatedMap datedMap = new DatedMapImpl();
        String testKey = "test";
        String testValue = "testValue";
        System.out.println(datedMap.get(testKey)); //null

        datedMap.put(testKey, testValue);
        datedMap.put("key", "value");
        datedMap.put("qwert", "trewq");

        System.out.println(datedMap.get(testKey)); // "testValue"
        System.out.println(datedMap.getKeyLastInsertionDate(testKey));
        System.out.println(datedMap.getKeyLastInsertionDate("key"));

        System.out.println(datedMap.containsKey(testKey)); // true
        System.out.println(datedMap.containsKey("noKey")); // false

        System.out.println(datedMap.keySet()); //[test, key, qwert]

        TimeUnit.SECONDS.sleep(2);
        datedMap.put(testKey, "newValue");
        System.out.println(datedMap.getKeyLastInsertionDate(testKey));
        System.out.println(datedMap.getKeyLastInsertionDate("key")); //разница в 2 секунды

        datedMap.remove(testKey);
        System.out.println(datedMap.getKeyLastInsertionDate(testKey)); //null

    }
}
