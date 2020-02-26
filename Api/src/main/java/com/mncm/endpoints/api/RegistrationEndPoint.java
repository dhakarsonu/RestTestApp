package com.mncm.endpoints.api;

import com.api.common.entity.common.Token;
import com.api.common.entity.contact.Contact;
import com.api.common.model.response.SignupResponse;
import com.api.common.utils.Preconditions;
import com.mncm.dao.TokenDao;
import com.mncm.daoimpl.RegistrationDaoImpl;
import com.mncm.dao.RegistrationDao;
import com.api.common.enums.Role;
import com.api.common.model.request.RegistrationRequestModel;
import com.mncm.daoimpl.TokenDaoImpl;
import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sonudhakar on 03/09/17.
 */

@Slf4j
@Path("/v1/signup")
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationEndPoint {

    private final RegistrationDao registrationDao;
    private final TokenDao tokenDao;


    public RegistrationEndPoint(){
        this.registrationDao = new RegistrationDaoImpl();
        this.tokenDao = new TokenDaoImpl();
    }

    @POST
    @Path("/process")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerCustomer(RegistrationRequestModel registrationRequestModel){

        ApiResponse apiResponse = new ApiResponse();
        Preconditions.checkArgument(registrationRequestModel == null,"invalid request");
        try{
            System.out.println("requesting coming"+registrationRequestModel);
            String tokenId = registrationDao.createNewRegistration(registrationRequestModel);

            if(tokenId != null) {
                apiResponse.setOk(true);
                apiResponse.add("token", tokenId);
            }

            System.out.println("response with "+tokenId);
        }catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }
        return Response.ok(apiResponse).build();
    }
}
