package com.mncm.exception.mapper;

import com.mncm.constants.Constant;
import com.mncm.model.response.ApiResponse;
import com.api.common.enums.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * OpenDigitalUniversity
 * Created by sonudhakar on 23/07/17.
 */
@Slf4j
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {

        log.error("Generic Exception : {}", ex.getMessage());

        ex.printStackTrace();

        log.error(ex.getMessage(), ex);

        ApiResponse response = new ApiResponse(false, ApiErrorCode.INTERNAL_SERVER_ERROR, "something went wrong on our end");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).type(Constant.JSON_CONTENTTYPE).build();
    }
}
