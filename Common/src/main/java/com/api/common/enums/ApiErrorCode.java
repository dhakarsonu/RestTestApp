package com.api.common.enums;

public enum ApiErrorCode implements ErrorCode {

    UNAUTHORIZED_REQUEST, NOT_FOUND, BAD_REQUEST, FORBIDDEN_REQUEST, INTERNAL_SERVER_ERROR, METHOD_NOT_ALLOWED, LIMITED_REACHED;

	@Override
	public String getErrorCode() {
		return this.name().toLowerCase();
	}

	@Override
	public String toString() {
		return getErrorCode();
	}

}
