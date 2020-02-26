package com.mncm.endpoints.api;

import com.api.common.entity.common.Token;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.services.Authentication;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.TokenDao;
import com.mncm.daoimpl.TokenDaoImpl;
import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by sonudhakar on 22/08/18.
 */
@Slf4j
@Path("/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationApi {

    private static final TokenDao tokenDao = new TokenDaoImpl();
    @Path("/access/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDetails(Map<String,String> data){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Preconditions.checkArgument(data == null,"invalid request");
            String jwToken = Authentication.createJWT(data);

            Token token = tokenDao.createToken(jwToken);
            if(token != null) {
                apiResponse.setOk(true);
                apiResponse.add("token", token.getId());
            }

        } catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }

        return Response.ok(apiResponse).build();
    }

    @Path("/access/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDetails(@QueryParam("token") String tokenId){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Preconditions.checkArgument(tokenId == null,"invalid request");

            Token token = tokenDao.get(tokenId);

            Preconditions.checkArgument(token == null,"invalid token");

            Map<String,Object> data = Authentication.parseJWT(token.getAccessToken());
            if(data != null) {
                apiResponse.setOk(true);
                apiResponse.setData(data);
            }

        } catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }

        return Response.ok(apiResponse).build();
    }

    @Path("/access/revoke")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response revokeAccessToken(@QueryParam("token") String tokenId){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Preconditions.checkArgument(tokenId == null,"invalid request");

            Token token = tokenDao.deleteToken(tokenId);
            if(token != null) {
                apiResponse.setOk(true);
            }

        } catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }

        return Response.ok(apiResponse).build();
    }
}
