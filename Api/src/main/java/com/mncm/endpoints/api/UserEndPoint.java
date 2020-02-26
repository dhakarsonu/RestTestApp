package com.mncm.endpoints.api;

import com.api.common.entity.common.Account;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.UserRole;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.model.response.AccountResponseModel;
import com.api.common.model.response.ContactResponse;
import com.api.common.model.response.LoginResponse;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.AccountDao;
import com.mncm.dao.RegistrationDao;
import com.mncm.dao.UserRoleDao;
import com.mncm.daoimpl.AccountDaoImpl;
import com.mncm.daoimpl.RegistrationDaoImpl;
import com.mncm.daoimpl.UserRoleDaoImpl;
import com.mncm.filter.AccessTokenCheck;
import com.mncm.model.response.ApiResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by sonudhakar on 29/04/18.
 */
@Slf4j
@Path("/v1/me")
@AccessTokenCheck
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndPoint {

    private final RegistrationDao registrationDao;
    private final UserRoleDao userRoleDao;
    private final AccountDao accountDao;

    public UserEndPoint(){
        this.registrationDao = new RegistrationDaoImpl();
        this.userRoleDao = new UserRoleDaoImpl();
        this.accountDao = new AccountDaoImpl();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDetails(@Context Contact currentUser){

        ApiResponse apiResponse = new ApiResponse();

        try {


            log.info("contact is"+currentUser);

            UserRole userRole = userRoleDao.getByUserId(currentUser.getId());
            if (userRole == null)
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "user does not have any role");

            Account account = accountDao.get(currentUser.getAccountId());
            if (account == null)
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "user does not have any account");

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setRole(userRole.getRole());

            AccountResponseModel accountResponseModel = accountDao.attachContactMethods(account);
            ContactResponse contactResponse = registrationDao.attachContactMethods(currentUser);

            loginResponse.setAccountResponse(accountResponseModel);
            loginResponse.setContactResponse(contactResponse);
            apiResponse.add("contact",loginResponse);
            apiResponse.setOk(true);


        } catch(EntityException | IllegalArgumentException e){
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
