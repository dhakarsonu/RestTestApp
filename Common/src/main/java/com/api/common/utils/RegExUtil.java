package com.api.common.utils;

import java.util.regex.Pattern;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public class RegExUtil {

    public static boolean isMatch(String regex, String src) {
        if (src == null)
            return false;

        try {
            return Pattern.matches(regex, src);
        } catch (Exception e) {
            return false;
        }
    }
}
