package com.common.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadUtil {

    private static final ThreadLocal ctx = new ThreadLocal();

    public static void put(Object key, Object value) {
        Map m = (Map) ctx.get();
        if (m == null) {
            m = new HashMap();
        }
        m.put(key, value);
        ctx.set(m);
    }

    public static Object get(Object key) {
        Map m = (Map) ctx.get();
        if (m != null) {
            return m.get(key);
        }
        return null;
    }

}
