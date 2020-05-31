package com.abee.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author xincong yao
 */
@Slf4j
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

    /**
     * Date format of mysql binlog
     * Tue Mar 03 08:00:00 CST 2020
     */
    public static Date parseStringDateFromBinlog(String s) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyy",
                    Locale.US
            );
            return DateUtils.addHours(dateFormat.parse(s), -8);
        } catch (ParseException e) {
            log.error("date format error: {}", s);
            return null;
        }
    }
}
