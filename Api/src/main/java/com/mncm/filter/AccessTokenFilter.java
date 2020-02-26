package com.mncm.filter;

import com.api.common.entity.common.Token;
import com.api.common.entity.contact.Contact;
import com.api.common.model.response.ApiResponse;
import com.api.common.services.Authentication;
import com.mncm.daoimpl.ContactMethodDaoImpl;
import com.mncm.daoimpl.RegistrationDaoImpl;
import com.mncm.daoimpl.TokenDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sonudhakar on 22/08/18.
 */
@Slf4j
@AccessTokenCheck
@Priority(2)
@Provider
public class AccessTokenFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {

        ApiResponse apiResponse = new ApiResponse();
        String tokenId = reqContext.getHeaderString("Authorization");
        tokenId = tokenId.replaceAll("Bearer ","");
        System.out.println(tokenId);
        Token token = new TokenDaoImpl().get(tokenId);
        if(token == null){
            apiResponse.setOk(false);
            apiResponse.add("msg","Authentication Failed!");
            abort(reqContext,apiResponse);
            return;
        }
        Map<String,Object> data = Authentication.parseJWT(token.getAccessToken());
        System.out.println(data);
        String contactId = (String) data.get("user_id");
        Contact contact = new RegistrationDaoImpl().get(contactId);

        if(contactId == null || contact == null){
            apiResponse.setOk(false);
            apiResponse.add("msg","Authentication Failed!");
            abort(reqContext,apiResponse);
            return;
        }

        ResteasyProviderFactory.pushContext(Contact.class, contact);
        reqContext.setProperty("currentUser", contact);


    }

    public void abort(ContainerRequestContext reqContext, ApiResponse response) {

        Response resp = Response.status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer realm=\"MNCM API\"")
                .type(MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
        reqContext.abortWith(resp);
    }
}
