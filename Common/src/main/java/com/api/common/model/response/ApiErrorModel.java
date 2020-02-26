package com.api.common.model.response;

import com.api.common.enums.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
@Data
@NoArgsConstructor
public class ApiErrorModel implements Serializable {

    private String code;
    private String message;

    public ApiErrorModel(ErrorCode errorCode, String message) {

        this.code = errorCode != null ? errorCode.toString() : null;
        this.message = message;
    }
}
