package com.mncm.endpoints.api;

import com.api.common.entity.common.Category;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.model.response.CollectionResponse;
import com.api.common.utils.Preconditions;
import com.mncm.daoimpl.InventoryDaoImpl;
import com.api.common.entity.IM.Product;
import com.api.common.model.request.InventoryRequestModel;
import com.mncm.dao.InventoryDao;
import com.mncm.model.request.BaseApiRequest;
import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;

/**
 * Created by sonudhakar on 03/09/17.
 */
@Slf4j
@Path("v1/inventory")
public class InventoryEndPoint extends AbstractBaseApiEndpoint{

    private final InventoryDao inventoryDao;

    public InventoryEndPoint(){
        this.inventoryDao = new InventoryDaoImpl();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInventory(InventoryRequestModel inventoryRequestModel){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(inventoryRequestModel == null,"request is not valid");

        try{
            if(inventoryRequestModel.getId() != null){
                Product product = inventoryDao.get(inventoryRequestModel.getId());
                if(product != null)
                    throw new EntityException(ApiErrorCode.BAD_REQUEST,"product already exist");
            }

            Product product = inventoryDao.createNewInvetory(inventoryRequestModel);
            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }else{
                apiResponse.setOk(false);
            }

        }catch (EntityException | IllegalArgumentException e){

            log.error(e.getMessage(),e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST,e.getMessage());
            return Response.status(400).entity(apiResponse).build();

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/{inventoryId}/remove")
    @DELETE
    public Response deleteInventory(@PathParam("inventoryId") String inventoryId){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(inventoryId == null,"inventoryId is not valid");

        try{

            Product product = inventoryDao.deleteInventory(inventoryId);
            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }else{
                apiResponse.setOk(false);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/{inventoryId}/update")
    @PUT
    public Response editInventory(@PathParam("inventoryId") String inventoryId,InventoryRequestModel inventoryRequestModel){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(inventoryId == null,"inventoryId not valid");
        Preconditions.checkArgument(inventoryRequestModel == null,"request payload is not valid");

        try{

            Product product = inventoryDao.get(inventoryId);
            if(product == null)
                throw new EntityException(ApiErrorCode.BAD_REQUEST,"product does not exist");

            product = inventoryDao.updateInvetory(product);

            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }else{
                apiResponse.setOk(false);
            }

        }catch (EntityException | IllegalArgumentException e){

            log.error(e.getMessage(),e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST,e.getMessage());
            return Response.status(400).entity(apiResponse).build();

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/item/{itemCode}/get")
    @GET
    public Response getInventoryByItemCode(@PathParam("itemCode") String itemCodel){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(itemCodel == null,"inventoryId not valid");

        try{

            Product product = inventoryDao.getByItemCode(itemCodel);

            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }else{
                apiResponse.setOk(false);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/item/{QRCode}/get")
    @GET
    public Response getInventoryByQRCode(@PathParam("QRCode") String QRCode){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(QRCode == null,"QRCode is not valid");

        try{

            Product product = inventoryDao.getByQRCode(QRCode);

            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }else{
                apiResponse.setOk(false);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/item/{barCode}/get")
    @GET
    public Response getInventoryBybarCode(@PathParam("barCode") String barCode){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(barCode == null,"barCode is not valid");

        try{

            Product product = inventoryDao.getByBarCode(barCode);

            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }else{
                apiResponse.setOk(false);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/category/{categoryId}/item/get")
    @GET
    public Response getInventoryByCategoryId(@PathParam("categoryId") String categoryid,@Form BaseApiRequest apiRequest){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(categoryid == null,"invalid categoryId");

        try{

            CollectionResponse<Product> collectionResponse = inventoryDao.getByCategoryId(categoryid,apiRequest.getLimit(), apiRequest.getCursor());

            if(collectionResponse != null) {
                apiResponse.add("items",collectionResponse.getItems());
                apiResponse.add("cursor",collectionResponse.getCursor());
                apiResponse.setOk(true);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/application/{applicationId}/item/get")
    @GET
    public Response getInventoryByApplicationId(@PathParam("applicationId") String applicationId,@Form BaseApiRequest apiRequest){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(applicationId == null,"invalid applicationId");

        try{

            CollectionResponse<Product> collectionResponse = inventoryDao.getByApplicationId(applicationId,apiRequest.getLimit(), apiRequest.getCursor());

            if(collectionResponse != null) {
                apiResponse.add("items",collectionResponse.getItems());
                apiResponse.add("cursor",collectionResponse.getCursor());
                apiResponse.setOk(true);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/{inventoryId}/get")
    @GET
    public Response getInventoryByinventoryId(@PathParam("inventoryId") String inventoryId){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(inventoryId == null,"inventoryId is not valid");

        try{

            Product product = inventoryDao.get(inventoryId);

            if(product != null) {
                apiResponse.setOk(true);
                apiResponse.add("item", product);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @Path("/migrate")
    @GET
    public Response migrateInventory(@Form BaseApiRequest apiRequest){

        ApiResponse apiResponse = new ApiResponse();

        try{

            CollectionResponse<Product> collectionResponse = inventoryDao.getByApplicationId("",500, apiRequest.getCursor());
            Iterator iterator = collectionResponse.getItems().iterator();
            while (iterator.hasNext()){
                Product product  = (Product) iterator.next();
                product.setQuantity(0);
                product.setOffer("");
                product.setCgst(0);
                product.setSgst(0);
                product.setGst(0);
                product.setDiscount(0);
                inventoryDao.saveInvetory(product);
            }

            if(collectionResponse.getItems() != null){
                apiResponse.setOk(true);
            }

        }catch(Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }
}
