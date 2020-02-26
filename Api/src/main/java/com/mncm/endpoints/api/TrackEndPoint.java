package com.mncm.endpoints.api;

import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sonudhakar on 03/09/17.
 */
@Slf4j
@Path("v1/track")
@Consumes(MediaType.APPLICATION_JSON)
public class TrackEndPoint {

    @Path("/get/{orderId}")
    @GET
    public Response getTrackDetail(@PathParam("orderId") String orderId){
        ApiResponse apiResponse = new ApiResponse();

        try{

        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok(apiResponse).build();
    }

    @Path("/create/{orderId}")
    @POST
    public Response createTrackDetail(@PathParam("orderId") String orderId){
        ApiResponse apiResponse = new ApiResponse();

        try{

        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok(apiResponse).build();
    }
}
