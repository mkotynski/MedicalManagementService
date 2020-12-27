package com.mkotynski.mmf.enums;

import java.util.HashMap;
import java.util.Map;

public enum RepeatablePeriod {
    EVERYDAY(0), 
    EVERYWEEK(1),
    EVERYMONTH(2);
    
    private int value;
    private static Map map = new HashMap<>();

    private RepeatablePeriod(int value) {
        this.value = value;
    }

    static {
        for (RepeatablePeriod pageType : RepeatablePeriod.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static RepeatablePeriod valueOf(int pageType) {
        return (RepeatablePeriod) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
