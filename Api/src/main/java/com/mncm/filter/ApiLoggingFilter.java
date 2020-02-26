package com.mncm.filter;

import com.google.apphosting.api.ApiProxy;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;

@Priority(1)
@Provider
@Slf4j
public class ApiLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String LOGGING_ID_PROPERTY = ApiLoggingFilter.class.getName() + ".id";
    private static final String REQUEST_ID_PROPERTY = "X-Request-ID";

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {

        String reqId = getRequestId();
        reqContext.setProperty(LOGGING_ID_PROPERTY, reqId);

        printRequestInfo(reqId, reqContext);
    }

    @Override
    public void filter(ContainerRequestContext reqContext, ContainerResponseContext respContext) throws IOException {

        String reqId = (String) reqContext.getProperty(LOGGING_ID_PROPERTY);
        if (reqId == null) {
            reqId = getRequestId();
            printRequestInfo(reqId, reqContext);
        }

        respContext.getHeaders().add(REQUEST_ID_PROPERTY, reqId);
    }

    private void printRequestInfo(String reqId, ContainerRequestContext reqContext) {
        log.info("REQUEST_ID_PROPERTY : {}", reqId);
    }

    public String getRequestId() {
        String id = (String) ApiProxy.getCurrentEnvironment().getAttributes().get("com.google.appengine.runtime.request_log_id");
        if (id == null)
            id = UUID.randomUUID().toString();
        return id;
    }
}
