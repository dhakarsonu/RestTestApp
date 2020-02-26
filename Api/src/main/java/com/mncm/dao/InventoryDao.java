package com.mncm.dao;

import com.api.common.entity.IM.Product;
import com.api.common.model.request.InventoryRequestModel;
import com.api.common.model.response.CollectionResponse;

/**
 * Created by sonudhakar on 09/09/17.
 */
public interface InventoryDao {

    public Product get(String id);
    public Product getByBarCode(String barCode);
    public Product getByQRCode(String QRCode);
    public CollectionResponse<Product> getByCategoryId(String categoryId, int limit, String cursorString);
    public CollectionResponse<Product> getByApplicationId(String applicationId,int limit, String cursorString);
    public Product getByItemCode(String itemCode);
    public Product createNewInvetory(InventoryRequestModel inventoryRequestModel) throws Exception;
    public Product updateInvetory(Product product) throws Exception;
    public Product saveInvetory(Product inventory);
    public Product deleteInventory(String id);
}
