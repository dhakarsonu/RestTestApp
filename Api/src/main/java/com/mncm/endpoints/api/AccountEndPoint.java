package com.mncm.endpoints.api;

import com.api.common.entity.address.Address;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.model.request.AddressRequestModel;
import com.api.common.model.response.ApiResponse;
import com.mncm.dao.AccountDao;
import com.mncm.daoimpl.AccountDaoImpl;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Slf4j
@Path("v1/address/account")
@Consumes(MediaType.APPLICATION_JSON)
public class AccountEndPoint {

    private final AccountDao accountDao;

    public AccountEndPoint(){
        accountDao = new AccountDaoImpl();
    }

    @POST
    @Path("/{accountId}/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAddress(@PathParam("accountId") String accountId, List<AddressRequestModel> requestModelList){
        ApiResponse apiResponse = new ApiResponse();

        try{
            log.info("coming here");
            List<Address> addressList = accountDao.saveAddress(accountId,requestModelList);

            if(addressList != null) {
                apiResponse.setOk(true);
                apiResponse.add("address",addressList);
            }
        } catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok(apiResponse).build();
    }
}
