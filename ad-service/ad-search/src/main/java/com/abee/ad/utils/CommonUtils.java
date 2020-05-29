package com.abee.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author xincong yao
 */
public class CommonUtils {

    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    public static String concat(String... args) {
        if (args.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s).append("-");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
