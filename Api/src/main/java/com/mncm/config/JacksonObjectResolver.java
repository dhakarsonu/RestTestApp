package com.mncm.config;

import com.mncm.model.JacksonObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * OpenDigitalUniversity
 * Created by Sonu on 23/07/17.
 */

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonObjectResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public JacksonObjectResolver() {
        super();
        mapper = new JacksonObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.mapper;
    }
}
