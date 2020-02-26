package com.mncm.endpoints.api;

import com.api.common.entity.common.Category;
import com.api.common.enums.CategoryType;
import com.api.common.exception.EntityException;
import com.api.common.model.response.CollectionResponse;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.CategoryDao;
import com.mncm.daoimpl.CategoryDaoImpl;
import com.api.common.model.request.CategoryRequestModel;
import com.mncm.model.request.BaseApiRequest;
import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sonudhakar on 24/01/18.
 */
@Slf4j
@Path("v1/category")
public class CategoryEndPoint extends AbstractBaseApiEndpoint{

    private final CategoryDao categoryDao;

    public CategoryEndPoint(){
        categoryDao = new CategoryDaoImpl();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(CategoryRequestModel categoryRequestModel)  throws Exception{

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(categoryRequestModel == null,"request is not valid");

        try{
            Category category = categoryDao.createNewCategory(categoryRequestModel);
            log.info("Category"+category);
            if(category != null) {
                apiResponse.add("category",ObjUtil.convertToMap(category));
                apiResponse.setOk(true);
            }

        } catch (EntityException e) {
            apiResponse.addError(e.getError(), e.getMessage());
            return Response.ok(apiResponse).build();
        }

        return Response.ok().entity(apiResponse).build();
    }

    @Path("/update")
    @POST
    public Response updateCategory(CategoryRequestModel categoryRequestModel){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(categoryRequestModel == null,"request is not valid");

        try{
            Category category = categoryDao.get(categoryRequestModel.getId());
            category = categoryDao.updateCategory(category);
            if(category != null) {
                apiResponse.add("category",ObjUtil.convertToMap(category));
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/{categoryId}/delete")
    @PUT
    public Response getById(@PathParam("categoryId") String categoryId){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(categoryId == null,"request is not valid");

        try{

            Category category = categoryDao.deleteCategory(categoryId);
            if(category != null) {
                apiResponse.add("category",ObjUtil.convertToMap(category));
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/{categoryId}/get")
    @GET
    public Response getByCategory(@PathParam("categoryId") String categoryId){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(categoryId == null,"request is not valid");

        try{

            Category category = categoryDao.get(categoryId);
            if(category != null) {
                apiResponse.add("category",ObjUtil.convertToMap(category));
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/type/{typeName}/get")
    @GET
    public Response getByType(@PathParam("typeName") CategoryType type){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(type == null,"request is not valid");

        try{

            Category category = categoryDao.getByType(type);
            if(category != null) {
                apiResponse.add("category",ObjUtil.convertToMap(category));
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/application/{applicationId}/get")
    @GET
    public Response getByApplicationId(@PathParam("applicationId") String applicationId,@Form BaseApiRequest apiRequest){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(applicationId == null,"request is not valid");

        try{

            CollectionResponse<Category> collectionResponse= categoryDao.getByApplicationId(applicationId,apiRequest.getLimit(), apiRequest.getCursor());
            if(collectionResponse != null) {
                apiResponse.add("categories",collectionResponse.getItems());
                apiResponse.add("cursor",collectionResponse.getCursor());
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

}
