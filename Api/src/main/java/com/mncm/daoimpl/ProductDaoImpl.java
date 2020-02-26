package com.mncm.daoimpl;

import com.api.common.entity.common.LinkedProduct;
import com.api.common.model.common.LinkedProductModel;
import com.api.common.model.request.ProductRequestModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.ProductDao;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by sonudhakar on 17/03/18.
 */
@Slf4j
public class ProductDaoImpl extends OfyService implements ProductDao {

    @Override
    public LinkedProduct get(String id) {
        return get(LinkedProduct.class, id);
    }

    @Override
    public LinkedProduct createAccount(ProductRequestModel productRequestModel) throws Exception{
        LinkedProductModel linkedProductModel = new LinkedProductModel(productRequestModel);

        if(ObjUtil.isBlank(linkedProductModel.getId())){
            linkedProductModel.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));
        }

        LinkedProduct  linkedProduct= new LinkedProduct(linkedProductModel);
        return saveAccount(linkedProduct);
    }

    @Override
    public LinkedProduct saveAccount(LinkedProduct account) throws Exception{
        return save(account) != null ? account : null;
    }

    @Override
    public CollectionResponse<LinkedProduct> getByAccountId(String accountId, int limit, String cursorString) throws Exception{

        Preconditions.checkArgument(ObjUtil.isBlank(accountId), "invalid accountId");

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<LinkedProduct> query = ofy().load().type(LinkedProduct.class);

        if (!ObjUtil.isBlank(accountId))
            query = query.filter("accountId", accountId);


        return fetchCursorQuery(query, limit, cursorString);
    }

    @Override
    public LinkedProduct deleteAccount(LinkedProduct account) throws Exception{

        return delete(account) ? account : null;

    }
}
