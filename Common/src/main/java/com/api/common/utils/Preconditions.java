package com.api.common.utils;

import com.api.common.enums.ErrorCode;
import com.api.common.exception.runtime.IllegalArgException;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public final class Preconditions {

    public static void checkArgument(boolean expression) {

        if (expression)
            throw new IllegalArgException();
    }

    public static void checkArgument(boolean expression, String message) {
        checkArgument(expression, null, message);
    }

    public static void checkArgument(boolean expression, ErrorCode errorCode, String message) {
        checkArgument(expression, errorCode, message, null);
    }

    public static void checkArgument(boolean expression, ErrorCode errorCode, String message, Object info) {

        if (expression)
            throw new IllegalArgException(errorCode, String.valueOf(message), info);
    }
}
