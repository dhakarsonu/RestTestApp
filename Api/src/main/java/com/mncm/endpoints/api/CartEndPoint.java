package com.mncm.endpoints.api;

import com.api.common.entity.IM.Product;
import com.api.common.entity.common.Cart;
import com.api.common.enums.ApiErrorCode;
import com.api.common.exception.EntityException;
import com.api.common.model.request.CartRequestModel;
import com.api.common.model.request.CategoryRequestModel;
import com.api.common.model.response.ApiResponse;
import com.api.common.model.response.CollectionResponse;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.CartDao;
import com.mncm.dao.InventoryDao;
import com.mncm.daoimpl.CartDaoImpl;
import com.mncm.daoimpl.InventoryDaoImpl;
import com.mncm.model.request.BaseApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Slf4j
@Path("v1/account/{accountId}/cart")
@Consumes(MediaType.APPLICATION_JSON)
public class CartEndPoint {

    private final CartDao cartDao = new CartDaoImpl();

    @POST
    @Path("/item/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCart(@PathParam("accountId") String accountId, CartRequestModel cartRequestModel){

        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"invalid accountId");
            Preconditions.checkArgument(cartRequestModel == null,"invalid request");

            cartRequestModel.setAccountId(accountId);

            Cart cart = cartDao.getByItemId(cartRequestModel.getItemId());
            if(cart != null)
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "item already added");

            cart = cartDao.createCart(cartRequestModel);

            if(cart != null){
                apiResponse.setOk(true);
                apiResponse.add("cart",cart);
            }

        }catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }
        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/item/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCart(@PathParam("accountId") String accountId, List<CartRequestModel> cartRequestModelList){

        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"invalid accountId");
            Preconditions.checkArgument(cartRequestModelList == null,"invalid request");

            List<Cart> carts = cartDao.updateCarts(cartRequestModelList,accountId);

            if(carts != null){
                apiResponse.setOk(true);
                apiResponse.add("carts",carts);
            }

        }catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }
        return Response.ok(apiResponse).build();
    }

    @DELETE
    @Path("/{cartId}/item/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCart(@PathParam("accountId") String accountId, @PathParam("cartId") String cartId){

        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"invalid accountId");
            Preconditions.checkArgument(ObjUtil.isBlank(cartId),"invalid cartId");

            Cart cart = cartDao.get(cartId);

            if(cart == null)
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "cart does not exist");
            else if(!cart.getAccountId().equals(accountId))
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "invalid cart");

            cart = cartDao.deleteCart(cart);

            if(cart != null){
                apiResponse.setOk(true);
                apiResponse.add("cart",cart);
            }

        }catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }
        return Response.ok(apiResponse).build();
    }

    @GET
    @Path("/{cartId}/item/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("accountId") String accountId, @PathParam("cartId") String cartId){

        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"invalid accountId");
            Preconditions.checkArgument(ObjUtil.isBlank(cartId),"invalid cartId");

            Cart cart = cartDao.get(cartId);

            if(cart == null)
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "cart does not exist");
            else if(!cart.getAccountId().equals(accountId))
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "invalid cart");

            if(cart != null){
                apiResponse.setOk(true);
                apiResponse.add("cart",cart);
            }

        }catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        } catch(Exception e){
            log.error(e.getMessage(),e);
            apiResponse.setOk(false);
        }
        return Response.ok(apiResponse).build();
    }

    @GET
    @Path("/item/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartByAccountId(@PathParam("accountId") String accountId,@Form BaseApiRequest apiRequest){

        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"invalid accountId");
            CollectionResponse<Cart> collectionResponse = cartDao.getByAccountId(accountId,apiRequest.getLimit(), apiRequest.getCursor());

            if(collectionResponse != null) {
                apiResponse.add("carts",collectionResponse.getItems());
                apiResponse.add("cursor",collectionResponse.getCursor());
                apiResponse.setOk(true);
            }

        }catch(EntityException | IllegalArgumentException e){
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
