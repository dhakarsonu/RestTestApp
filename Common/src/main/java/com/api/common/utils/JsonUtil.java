package com.api.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.api.common.model.JacksonObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public final class JsonUtil {

    public static Map<String, Object> convertJsonToMap(String json) {

        try {
            return new JacksonObjectMapper().readValue(json,
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T convertSafeToType(String json, Class<T> clazz) {
        try {
            return new JacksonObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T convertObjToType(Object data, Class<T> clazz) {

        JacksonObjectMapper objMapper = new JacksonObjectMapper();
        return objMapper.convertValue(data, clazz);
    }

    public static <T> List<T> convertJsonToList(String json, Class<T> type) throws IOException {
        return convertJsonString(json, TypeFactory.defaultInstance().constructCollectionType(List.class, type));
    }

    public static <T> T convertJsonString(String json, JavaType type) throws IOException {
        return new JacksonObjectMapper().readValue(json, type);
    }
}
