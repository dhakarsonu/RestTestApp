package com.mncm.daoimpl;

import com.api.common.entity.common.Category;
import com.api.common.enums.EntityStatus;
import com.api.common.model.response.CollectionResponse;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.RandomUtil;
import com.api.common.entity.IM.Product;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.InventoryDao;
import com.api.common.model.request.InventoryRequestModel;
import com.api.common.model.IM.ProductModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 09/09/17.
 */
@Slf4j
public class InventoryDaoImpl   extends OfyService implements InventoryDao {

    @Override
    public Product get(String id){
        return get(Product.class,id);
    }

    @Override
    public Product getByBarCode(String barCode){
        if (barCode == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Product.class).filter("barCode", barCode).first().now();
    }

    @Override
    public Product getByQRCode(String QRCode){
        if (QRCode == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Product.class).filter("QRCode", QRCode).first().now();
    }

    @Override
    public CollectionResponse<Product> getByCategoryId(String categoryId,int limit, String cursorString){
        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Product> query = ofy().load().type(Product.class);

        if (!ObjUtil.isBlank(categoryId))
            query = query.filter("categoryId", categoryId);


        return fetchCursorQuery(query, limit, cursorString);
    }

    @Override
    public CollectionResponse<Product> getByApplicationId(String applicationId,int limit, String cursorString){

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Product> query = ofy().load().type(Product.class);

        if (!ObjUtil.isBlank(applicationId))
            query = query.filter("applicationId", applicationId);


        return fetchCursorQuery(query, limit, cursorString);
    }

    @Override
    public Product getByItemCode(String itemCode){
        if (itemCode == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(Product.class).filter("itemCode", itemCode).first().now();
    }

    @Override
    public Product createNewInvetory(InventoryRequestModel inventoryRequestModel) throws Exception{

        ProductModel productModel = new ProductModel(inventoryRequestModel);

        Product inventory = new Product(productModel);

        if(productModel.getId() != null){
            inventory.setId(productModel.getId());
        }else{
            inventory.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        return saveInvetory(inventory);

    }

    @Override
    public Product updateInvetory(Product product) throws Exception{

        return saveInvetory(product);

    }

    @Override
    public Product saveInvetory(Product inventory){

        return save(inventory) != null ? inventory : null;

    }

    @Override
    public Product deleteInventory(String id){

        Product inventory = get(id);

        if(inventory == null)
            return null;

        return delete(inventory) ? inventory : null;

    }
}
