package com.mncm.model;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public class JacksonObjectMapper extends ObjectMapper {

    public JacksonObjectMapper(){
        this(true);
    }

    public JacksonObjectMapper(boolean ignoreNull) {
        super();

        if(ignoreNull)
            this.setSerializationInclusion(Include.NON_NULL);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
