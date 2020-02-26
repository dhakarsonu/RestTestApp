package com.api.common.constants;

import com.google.common.collect.ImmutableSet;
import com.api.common.enums.AppMode;
import com.api.common.services.cache.MCacheService;
import com.api.common.utils.GaeUtil;

import java.util.Set;

/**
 * Created by sonudhakar on 22/07/17.
 */
public class Constant {

    public static final String JSON_CONTENTTYPE = "application/json; charset=utf-8";

    public static final AppMode APP_MODE;

    public static final MCacheService noExpiryCache = new MCacheService();

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
        return (APP_MODE == AppMode.LIVE) ? "assets.anywhereworks.com" : "assets.staging.anywhereworks.com";
    }

    public static final String getAppDomain() {

        if (APP_MODE == AppMode.DEV)
            return "localhost:";

        return (APP_MODE == AppMode.LIVE) ? "anywhereworks.com" : "staging.anywhereworks.com";
    }

    public static String geApiDomain() {

        String appDomain = getAppDomain();
        if (APP_MODE == AppMode.DEV) {
            return "http://" + appDomain + "8885";
        }

        return "https://api." + getAppDomain();
    }

    public static final String getDevConsoleUrl() {

        if (APP_MODE == AppMode.DEV)
            return "http://localhost:8884";

        String domain = (APP_MODE == AppMode.LIVE) ? "anywhereworks.com" : "staging.anywhereworks.com";
        return "https://developer." + domain;
    }

    public static Set<String> allowedAWDevs() {
        return ImmutableSet.of("ramesh.lingappa@a-cti.com", "madhavan.chidambaram@a-cti.com", "shaikanjavali.mastan@a-cti.com", "malleswari.srinivasarao@a-cti.com");
    }

    public static String getFullAuthDomain() {
        return (APP_MODE == AppMode.LIVE) ? "fullcreative" : "staging-fullcreative";
    }


}
