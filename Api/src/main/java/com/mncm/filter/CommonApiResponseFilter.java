package com.mncm.filter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/* Applies to all response */
@Priority(Priorities.HEADER_DECORATOR)
@Provider
public class CommonApiResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext reqContext, ContainerResponseContext respContext) throws IOException {

//        respContext.getHeaders().add("X-Frame-Options", "SAMEORIGIN");
//        respContext.getHeaders().add("X-Content-Type-Options", "nosniff");
//        respContext.getHeaders().add("X-XSS-Protection", "1; mode=block");
//
//        // force https only
//        //respContext.getHeaders().add("Strict-Transport-Security", "max-age=31536000");
//
//        respContext.getHeaders().add("Cache-Control", "private, no-cache, max-age=0, must-revalidate");
//        respContext.getHeaders().add("Pragma", "no-cache");
//
//        respContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
//        respContext.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
//        respContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
        respContext.getHeaders().add("X-Frame-Options", "SAMEORIGIN");
        respContext.getHeaders().add("X-Content-Type-Options", "nosniff");
        respContext.getHeaders().add("X-XSS-Protection", "1; mode=block");

        // force https only
        respContext.getHeaders().add("Strict-Transport-Security", "max-age=31536000");

        respContext.getHeaders().add("Cache-Control", "private, no-cache, max-age=0, must-revalidate");
        respContext.getHeaders().add("Pragma", "no-cache");
    }

}
