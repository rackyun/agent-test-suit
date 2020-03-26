package com.keep.infra.agent.test.suit.keep.monitor.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
public class FalconContext {

    private static final Map<String, String> records = new ConcurrentHashMap<>();

    public static void save(String host, String record) {
        records.put(host, record);
    }

    public static String get(String host) {
        return records.get(host);
    }
}
