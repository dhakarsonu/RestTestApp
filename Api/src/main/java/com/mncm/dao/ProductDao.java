package com.mncm.dao;

import com.api.common.entity.common.LinkedProduct;
import com.api.common.model.request.ProductRequestModel;
import com.api.common.model.response.CollectionResponse;

/**
 * Created by sonudhakar on 17/03/18.
 */
public interface ProductDao {

    public LinkedProduct createAccount(ProductRequestModel productRequestModel) throws Exception;
    public LinkedProduct saveAccount(LinkedProduct account) throws Exception;
    public CollectionResponse<LinkedProduct> getByAccountId(String accountId, int limit, String cursorString) throws Exception;
    public LinkedProduct deleteAccount(LinkedProduct account) throws Exception;
    public LinkedProduct get(String id);

}
