package com.mncm.exception.mapper;

import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.runtime.IllegalArgException;
import com.mncm.model.response.ApiResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
public class IllegalArgExceptionMapper implements ExceptionMapper<IllegalArgException> {

    @Override
    public Response toResponse(IllegalArgException e) {

        ApiResponse resp = new ApiResponse(false, e.getError() != null ? e.getError() : ApiErrorCode.BAD_REQUEST, e.getMessage() == null ? "that was a bad request" : e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(resp).type(MediaType.APPLICATION_JSON).build();
    }
}
