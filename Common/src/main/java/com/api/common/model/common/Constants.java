package com.api.common.model.common;

import com.api.common.enums.AppMode;
import com.api.common.utils.GaeUtil;
import com.google.common.collect.ImmutableSet;

/**
 * Created by sonudhakar on 24/02/18.
 */
public class Constants {

    public static final AppMode APP_MODE;
    public static final String JSON_CONTENTTYPE = "application/json; charset=utf-8";

    static {

        APP_MODE = GaeUtil.getAppMode();

        switch (APP_MODE) {
            case LIVE:


                break;
            default:


                break;
        }
    }

    public static final String gcsBucketName() {
        return (APP_MODE == AppMode.LIVE) ? "mncm-196208.appspot.com" : "staging.mncm-196208.appspot.com";
    }

}
