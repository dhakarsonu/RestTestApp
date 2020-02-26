package com.api.common.enums;

public enum EntityErrorCode implements ErrorCode {

    CREATE_FAILED, UPDATE_FAILED, ALREADY_EXISTS, NOT_FOUND,

    //delete
    DELETE_FAILED,
    ALREADY_DELETED,

    //disable
    ALREADY_DISABLED, DISABLED_FAILED;

    @Override
    public String getErrorCode() {
        return name().toLowerCase();
    }

    @Override
    public String toString() {
        return getErrorCode();
    }

}
