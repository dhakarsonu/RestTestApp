package com.api.common.exception;

import com.api.common.enums.ErrorCode;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public class ForbiddenException extends AbstractException {

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(ErrorCode error, String msg) {
        super(error, msg);
    }

    public ForbiddenException(ErrorCode error, String msg, Object info) {
        super(error, msg, info);
    }
}
