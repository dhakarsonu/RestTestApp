package com.api.common.utils;

import com.google.appengine.api.utils.SystemProperty;
import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Key;
import com.api.common.annotation.ETagCache;
import com.api.common.constants.Constant;
import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.AppMode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
@Slf4j
public final class GaeUtil {

    /*
    * Is Appengine Environment is Development
    * */
    public static boolean isDevEnvironment() {
        return (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development);
    }

    public static AppMode getAppMode() {

        if (GaeUtil.isDevEnvironment()) {
            return AppMode.DEV;
        } else if (GaeUtil.getAppId().contains("live-fullspectrum")) {
            return AppMode.LIVE;
        }
        return AppMode.STAGING;
    }

    public static String getAppId() {
        return System.getProperty(SystemProperty.applicationId.key());
    }

    public static String getAppspotMailId(String prefix) {
        return prefix + "@" + getAppId() + ".appspotmail.com";
    }

    public static String getServerUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName();
    }

    public static String getServerUrl(HttpServletRequest request, AppMode mode) {
        return mode != AppMode.DEV ? getServerUrl(request) : getServerUrl(request) + ":" + request.getServerPort();
    }

    public static void updateEntityETagInCache(AbstractBaseEntity entity) {

        if (!entity.getClass().isAnnotationPresent(ETagCache.class))
            return;

        try {

            String etagKey = (entity == null) ? null : getEntityETagCacheKey(entity.getClass(), entity.getId());

            //log.info("caching etag {} : {}", etagKey, entity.hash());
            if (etagKey != null)
                Constant.noExpiryCache.put(etagKey, entity.hash());
            else
                log.warn("failed to generated etag key for entity : {}", entity);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static boolean deleteEntityETagInCache(AbstractBaseEntity entity) {

        if (entity == null || !entity.getClass().isAnnotationPresent(ETagCache.class))
            return false;

        return deleteEtagCacheForKey(getEntityETagCacheKey(entity.getClass(), entity.getId()));
    }

    public static boolean deleteEntityETagInCache(Key<?> key) {
        return deleteEtagCacheForKey(getEntityETagCacheKey(key));
    }

    public static boolean deleteEtagCacheForKey(String etagKey) {

        if (etagKey == null)
            return false;

        try {
            return Constant.noExpiryCache.remove(etagKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }


    public static String getEntityETagValueFromCache(Class clazz, String id) {

        String etagKey = getEntityETagCacheKey(clazz, id);
        return (etagKey == null) ? null : Constant.noExpiryCache.get(etagKey, String.class);
    }

    public static String getEntityETagCacheKey(Class clazz, String id) {

        if (clazz != null && id != null) {
            return getEntityETagCacheKey(Key.create(clazz, id));
        }
        return null;
    }

    public static String getEntityETagCacheKey(Key<?> key) {
        return key == null ? null : "etag-" + key.toWebSafeString();
    }


    public static Set<String> convertToSearchTokens(String src) {

        if (ObjUtil.isBlank(src))
            return null;

        src = src.toLowerCase().trim();
        if (src.length() <= 3)
            return ImmutableSet.of(src);

        String prefix = src.substring(0, 3);
        Set<String> tokens = new HashSet<>();

        tokens.add(prefix);
        StringBuilder builder = new StringBuilder(prefix);

        for (int i = 3; i < src.length(); i++) {
            builder.append(src.charAt(i));
            tokens.add(builder.toString());
        }

        StringTokenizer st = new StringTokenizer(src);

        while (st.hasMoreElements()) {
            String el = st.nextToken().trim();
            if (el.length() <= 1)
                continue;
            tokens.add(el);
        }
        return tokens;
    }
}
