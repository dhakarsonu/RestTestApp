package com.mncm.exception.mapper;

import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.ForbiddenException;
import com.mncm.model.response.ApiResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException e) {

        ApiResponse resp = new ApiResponse(false, ApiErrorCode.FORBIDDEN_REQUEST, e.getMessage() == null ? "you are not authorized for this action" : e.getMessage());
        return Response.status(Response.Status.FORBIDDEN).entity(resp).type(MediaType.APPLICATION_JSON).build();
    }
}
