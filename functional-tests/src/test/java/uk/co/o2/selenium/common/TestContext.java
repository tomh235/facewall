package uk.co.o2.selenium.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds details about the execution of a test as it goes through. This should be cleaned down at the beginning/end of each test.
 */
public class TestContext {

    public enum Keys {
        UpgradeUserMsisdn,
        UpgradeUserUpgraded
    }

    private static Map<Keys, Object> contextMap = new HashMap<Keys, Object>();

    public static void put(Keys key, Object value){
        contextMap.put(key, value);
    }

    public static <T> T get(Keys key, Class<T> outAsType){
        return (T)contextMap.get(key);
    }

    public static boolean hasKey(Keys key){
        return contextMap.containsKey(key);
    }

    public static void cleanContext(){
        contextMap.clear();
    }
}
