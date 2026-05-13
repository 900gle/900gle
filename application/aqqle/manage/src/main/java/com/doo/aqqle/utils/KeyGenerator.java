package com.doo.aqqle.utils;

public class KeyGenerator {
    public static Object cacheKey(String prefix, Object o1, Object o2) {
        return prefix+":" + o1 + ":" + o2;
    }
}
