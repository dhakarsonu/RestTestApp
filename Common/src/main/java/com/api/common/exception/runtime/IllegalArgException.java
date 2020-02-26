package com.api.common.exception.runtime;

import com.api.common.enums.ErrorCode;

/**
 * FullAuth Created by Ramesh on 10/28/15.
 */
public class IllegalArgException extends AbstractRuntimeException {

    public IllegalArgException() {
        super();
    }

    public IllegalArgException(String message) {
        super(message);
    }

    public IllegalArgException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgException(ErrorCode error) {
        super(error);
    }

    public IllegalArgException(ErrorCode error, String msg) {
        super(error, msg);
    }

    public IllegalArgException(ErrorCode error, String msg, Object info) {
        super(error, msg, info);
    }
}
