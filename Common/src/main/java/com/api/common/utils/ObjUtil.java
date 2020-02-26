package com.api.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.appengine.api.datastore.Text;
import com.api.common.model.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
@Slf4j
public final class ObjUtil {

    public static boolean isNullOrEmpty(String value) {
        return (value == null || value.length() <= 0);
    }

    public static boolean isBlank(Text value) {
        return (value == null || isBlank(value.getValue()));
    }

    public static boolean isBlank(String value) {
        return (value == null || value.trim().length() <= 0);
    }

    public static boolean isNullOrEmpty(Collection<?> obj) {
        return (obj == null || obj.isEmpty());
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    public static boolean isRequestNullOrEmpty(String... requestParams) {

        boolean resp = false;
        for (int i = 0; i < requestParams.length; i++) {
            if (requestParams[i] == null || requestParams[i] == "" || requestParams[i].trim() == "") {
                resp = true;
                break;
            }
        }
        return resp;
    }

    public static String getJson(Object object) {

        try {
            return new JacksonObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static byte[] getJsonAsBytes(Object object) {

        try {
            return new JacksonObjectMapper().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static Map<String, Object> getMapFromJson(String json) {

        try {
            return new JacksonObjectMapper().readValue(json,
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T safeConvertJson(String json, Class<T> clazz) {
        try {
            return new JacksonObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static Map<String, Object> convertToMap(Object data) {

        if (data == null)
            return null;
        return new JacksonObjectMapper().convertValue(data, new TypeReference<Map<String, Object>>() {
        });
    }

    public static String toFormParams(Object obj) {

        Map map = new JacksonObjectMapper().convertValue(obj, Map.class);
        return mapToUtf8FormParam(map);
    }

    /**
     * Map to form param.
     *
     * @param map the map
     * @return the string
     */
    public static String mapToUtf8FormParam(Map<String, Object> map) {

        if (map == null)
            return null;

        StringBuilder builder = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {

                if (builder.length() > 0) {
                    builder.append("&");
                }

                Object value = entry.getValue();
                if (value instanceof String) {
                    value = URLEncoder.encode((String) value, "UTF-8");
                }

                builder.append(String.format("%s=%s", entry.getKey(), value));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static int safeParseInt(String numStr) {
        try {
            return Integer.parseInt(numStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
