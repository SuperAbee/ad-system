package com.abee.ad.utils;

import com.abee.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author xincong yao
 */
public class CommonUtils {

    private static String[] datePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String s) {
        return DigestUtils.md5Hex(s);
    }

    public static Date parseDate(String date) throws AdException {
        try {
            return DateUtils.parseDate(date, datePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }
}
