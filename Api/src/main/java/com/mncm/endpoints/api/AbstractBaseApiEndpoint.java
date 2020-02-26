package com.mncm.endpoints.api;

import com.api.common.constants.Constant;
import com.api.common.utils.GaeUtil;
import com.api.common.model.common.Constants;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * AnywhereWorks Created by Ramesh on 2/10/16.
 */
@Cache(isPrivate = true, mustRevalidate = true, proxyRevalidate = true, maxAge = 0)
@Produces(Constants.JSON_CONTENTTYPE)
@Consumes(Constants.JSON_CONTENTTYPE)
public abstract class AbstractBaseApiEndpoint {

    @Context
    protected HttpServletRequest servletRequest;

    @Context
    HttpServletResponse servletResponse;

    @Context
    Request request;

    protected Response badRequest(Object obj) {
        return Response.status(Response.Status.BAD_REQUEST).entity(obj).build();
    }

    public Response ok(Object obj, EntityTag tag) {

        Response.ResponseBuilder builder = Response.ok(obj);
        if (tag != null)
            builder.tag(tag);

        return builder.build();
    }

    protected Response etagValidation(Class clazz, String id) {

        String etagValue = GaeUtil.getEntityETagValueFromCache(clazz, id);

        System.out.println("etag : " + etagValue);
        if (etagValue != null) {
            EntityTag entityTag = new EntityTag(etagValue);

            Response.ResponseBuilder rb = request.evaluatePreconditions(entityTag);
            if (rb != null)
                return rb.tag(entityTag).build();
        }
        return null;
    }
}
