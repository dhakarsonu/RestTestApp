package com.mncm.endpoints.api;

import com.api.common.entity.common.Account;
import com.api.common.entity.common.Token;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.model.response.AccountResponseModel;
import com.api.common.model.response.LoginResponse;
import com.api.common.services.Authentication;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.AccountDao;
import com.mncm.dao.TokenDao;
import com.mncm.daoimpl.AccountDaoImpl;
import com.mncm.daoimpl.RegistrationDaoImpl;
import com.mncm.daoimpl.TokenDaoImpl;
import com.mncm.daoimpl.UserRoleDaoImpl;
import com.mncm.dao.RegistrationDao;
import com.mncm.dao.UserRoleDao;
import com.api.common.entity.contact.Contact;
import com.api.common.entity.contact.UserRole;
import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sonudhakar on 03/09/17.
 */
@Slf4j
@Path("v1/user")
@Consumes(MediaType.APPLICATION_JSON)
public class LoginEndPoint {

    private final RegistrationDao registrationDao;
    private final UserRoleDao userRoleDao;
    private final AccountDao accountDao;
    private final TokenDao tokenDao;

    public LoginEndPoint(){
        this.registrationDao = new RegistrationDaoImpl();
        this.userRoleDao = new UserRoleDaoImpl();
        this.accountDao = new AccountDaoImpl();
        this.tokenDao = new TokenDaoImpl();
    }
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("isSocial") boolean isSocial, HashMap<String,String> loginRequest){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(loginRequest == null,"request is not valid");

        String login = loginRequest.get("login");
        String password = loginRequest.get("password");

        Preconditions.checkArgument(ObjUtil.isNullOrEmpty(login),"invalid email");

        try {

            Contact contact = registrationDao.getByEmail(login);

            log.info("contact is"+contact);

            if (contact != null && (password != null && (password.equals(contact.getPassword())) || isSocial)) {
                UserRole userRole = userRoleDao.getByUserId(contact.getId());
                if (userRole == null)
                    throw new EntityException(ApiErrorCode.BAD_REQUEST, "user does not have any role");

                Account account = accountDao.get(contact.getAccountId());
                if (account == null)
                    throw new EntityException(ApiErrorCode.BAD_REQUEST, "user does not have any account");
                Map<String,String> request = new HashMap<>();
                request.put("user_id",contact.getId());
                request.put("scopes","mncm.full.access,lixiram.full.access");
                String jwt = Authentication.createJWT(request);
                if(jwt != null){
                    Token token = tokenDao.getByEmail(login);
                    if(token != null){
                        token.setAccessToken(jwt);
                        token = tokenDao.saveToken(token);
                    }else {
                        token = tokenDao.createToken(jwt);
                    }
                    if(token != null) {
                        apiResponse.setOk(true);
                        apiResponse.add("token", token.getId());
                    }
                }
            }

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
