package com.mncm.endpoints.api;

import com.api.common.entity.common.App;
import com.api.common.model.request.AppRequestModel;
import com.api.common.model.response.ApiResponse;
import com.api.common.model.response.CollectionResponse;
import com.api.common.utils.Preconditions;
import com.mncm.dao.AppDao;
import com.mncm.daoimpl.AppDaoImpl;
import com.mncm.model.request.BaseApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sonudhakar on 27/01/18.
 */
@Slf4j
@Path("v1/application")
public class AppEndPoint {

    private final AppDao appDao;

    public AppEndPoint(){
        appDao = new AppDaoImpl();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addApplication(AppRequestModel appRequestModel)  throws Exception{

        ApiResponse response = new ApiResponse();

        Preconditions.checkArgument(appRequestModel == null,"request is not valid");

        try{
            App application = appDao.createNewApplication(appRequestModel);
            if(application != null){
                response.setOk(true);
                response.add("application",application);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok().entity(response).build();
    }

    @PUT
    @Path("/{applicationId}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteApplication(@PathParam("applicationId") String applicationId)  throws Exception{

        ApiResponse response = new ApiResponse();

        Preconditions.checkArgument(applicationId == null,"request is not valid");

        try{
            App application = appDao.deleteApplication(applicationId);
            if(application != null){
                response.setOk(true);
                response.add("application",application);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{applicationId}/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplication(@PathParam("applicationId") String applicationId)  throws Exception{

        ApiResponse response = new ApiResponse();

        Preconditions.checkArgument(applicationId == null,"request is not valid");

        try{
            App application = appDao.get(applicationId);
            if(application != null){
                response.setOk(true);
                response.add("application",application);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplications(@Form BaseApiRequest apiRequest)  throws Exception{

        ApiResponse response = new ApiResponse();

        try{
            CollectionResponse<App> apps = appDao.getAll(apiRequest.getLimit(),apiRequest.getCursor());
            if(apps != null){
                response.setOk(true);
                response.add("applications",apps.getItems());
                response.add("cursor",apps.getCursor());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return Response.ok().entity(response).build();
    }
}
