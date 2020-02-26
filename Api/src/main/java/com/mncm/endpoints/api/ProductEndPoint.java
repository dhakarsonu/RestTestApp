package com.mncm.endpoints.api;

import com.api.common.entity.common.LinkedProduct;
import com.api.common.entity.IM.Product;
import com.api.common.model.request.ProductRequestModel;
import com.api.common.model.response.ApiResponse;
import com.api.common.model.response.CollectionResponse;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.ProductDao;
import com.mncm.dao.InventoryDao;
import com.mncm.daoimpl.ProductDaoImpl;
import com.mncm.daoimpl.InventoryDaoImpl;
import com.mncm.model.request.BaseApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Form;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sonudhakar on 17/03/18.
 */
@Slf4j
@Path("v1/account/{accountId}")
public class ProductEndPoint extends AbstractBaseApiEndpoint{

    private final ProductDao productDao = new ProductDaoImpl();

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts(@PathParam("accountId") String accountId,@Form BaseApiRequest apiRequest){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(accountId == null,"invalid accountId");

        try{

            CollectionResponse<LinkedProduct> collectionResponse= productDao.getByAccountId(accountId,apiRequest.getLimit(), apiRequest.getCursor());

            List<Product> products = new ArrayList<>();
            InventoryDao inventoryDao = new InventoryDaoImpl();

            for (LinkedProduct account : collectionResponse.getItems()) {
                Product product = inventoryDao.get(account.getProductId());
                if (product != null)
                    products.add(product);
            }

            if(collectionResponse != null) {
                apiResponse.add("accounts",collectionResponse.getItems());
                apiResponse.add("products",products);
                apiResponse.add("cursor",collectionResponse.getCursor());
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }


    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("accountId") String accountId,ArrayList<String> ids){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(accountId == null,"invalid accountId");
        Preconditions.checkArgument(ids == null,"invalid request");

        try{
            Iterator iterator = ids.iterator();
            ArrayList<LinkedProduct> linkedProducts = new ArrayList<>();
            while (iterator.hasNext()){
                String id = (String) iterator.next();
                LinkedProduct linkedProduct = productDao.get(id);
                if(linkedProduct != null) {
                    linkedProduct = productDao.deleteAccount(linkedProduct);
                    if(linkedProduct != null){
                        InventoryDao inventoryDao = new InventoryDaoImpl();
                        Product product = inventoryDao.get(linkedProduct.getProductId());
                        int quantity = product.getQuantity();
                        quantity = quantity - linkedProduct.getQuantity();
                        quantity = quantity < 0 ? 0 : quantity;
                        product.setQuantity(quantity);
                        inventoryDao.saveInvetory(product);
                    }
                    linkedProducts.add(linkedProduct);
                }
            }

            if(linkedProducts != null) {
                apiResponse.add("accounts",linkedProducts);
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProducts(@PathParam("accountId") String accountId, ArrayList<ProductRequestModel> productRequestModels){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(accountId == null,"invalid accountId");
        Preconditions.checkArgument(productRequestModels == null,"invalid request");

        try{

            Iterator iterator = productRequestModels.iterator();
            ArrayList<LinkedProduct> linkedProducts = new ArrayList<>();
            while (iterator.hasNext()){

                ProductRequestModel productRequestModel = (ProductRequestModel) iterator.next();
                LinkedProduct account = productDao.createAccount(productRequestModel);
                linkedProducts.add(account);
            }

            if(!ObjUtil.isNullOrEmpty(linkedProducts)) {
                apiResponse.add("accounts",linkedProducts);
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProducts(@PathParam("accountId") String accountId, ArrayList<ProductRequestModel> productRequestModels){

        ApiResponse apiResponse = new ApiResponse();

        Preconditions.checkArgument(accountId == null,"invalid accountId");
        Preconditions.checkArgument(productRequestModels == null,"invalid request");

        try{

            Iterator iterator = productRequestModels.iterator();
            ArrayList<LinkedProduct> accounts = new ArrayList<>();
            while (iterator.hasNext()){

                ProductRequestModel productRequestModel = (ProductRequestModel) iterator.next();
                LinkedProduct linkedProduct = productDao.get(productRequestModel.getId());
                int quanityUpdate = (productRequestModel.getQuantity() - linkedProduct.getQuantity());
                if(productRequestModel.getQuantity() != linkedProduct.getQuantity()){
                    linkedProduct.setQuantity(productRequestModel.getQuantity());
                    linkedProduct = productDao.saveAccount(linkedProduct);
                }
                //Updating product totoal quantity
                if(linkedProduct != null){
                    InventoryDao inventoryDao = new InventoryDaoImpl();
                    Product product = inventoryDao.get(linkedProduct.getProductId());
                    int quantity = product.getQuantity();
                    quantity = quantity + quanityUpdate;
                    quantity = quantity < 0 ? 0 : quantity;
                    product.setQuantity(quantity);
                    inventoryDao.saveInvetory(product);
                }
                accounts.add(linkedProduct);
            }

            if(!ObjUtil.isNullOrEmpty(accounts)) {
                apiResponse.add("accounts",accounts);
                apiResponse.setOk(true);
            }

        }catch (Exception e){
            log.info(e.getMessage(),e);
        }
        return Response.ok(apiResponse).build();
    }
}
