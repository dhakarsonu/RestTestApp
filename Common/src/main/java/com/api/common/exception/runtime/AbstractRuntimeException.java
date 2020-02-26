package com.api.common.exception.runtime;

import com.api.common.enums.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * FullAuth Created by Ramesh on 10/28/15.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractRuntimeException extends RuntimeException {

    private ErrorCode error;

    private Object info;

    public AbstractRuntimeException(String message) {
        super(message);
    }

    public AbstractRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractRuntimeException(Throwable cause) {
        super(cause);
    }

    public AbstractRuntimeException(ErrorCode error) {
        this(error, null);
    }

    public AbstractRuntimeException(ErrorCode error, String msg) {
        this(error, msg, null);
    }

    public AbstractRuntimeException(ErrorCode error, String msg, Object info) {
        super(msg);
        this.error = error;
        this.info = info;
    }

    public boolean hasError() {
        return error != null;
    }

    public String errorCode() {
        return error == null ? null : error.getErrorCode();
    }
}
