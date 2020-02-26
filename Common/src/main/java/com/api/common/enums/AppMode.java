package com.api.common.enums;

/**
 * OpenDigitalUniversity
 * Created by Sonu on 23/07/17.
 */
public enum AppMode {
    DEV, STAGING, LIVE;

    public boolean isLive() {
        return this == LIVE;
    }
}
