package com.mncm.daoimpl;

import com.api.common.entity.common.Cart;
import com.api.common.enums.EntityStatus;
import com.api.common.model.common.CartModel;
import com.api.common.model.request.CartRequestModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.CartDao;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Slf4j
public class CartDaoImpl extends OfyService implements CartDao{

    @Override
    public Cart get(String id){
        return get(Cart.class,id);
    }

    @Override
    public Cart getByItemId(String itemId)throws Exception{

        return ofy().load().type(Cart.class).filter("itemId",itemId).first().now();
    }

    @Override
    public Cart createCart(CartRequestModel requestModel) throws Exception{


        Preconditions.checkArgument(ObjUtil.isBlank(requestModel.getAccountId()),"invalid accountid");
        Preconditions.checkArgument(ObjUtil.isBlank(requestModel.getContactId()),"invalid contactId");
        Preconditions.checkArgument(ObjUtil.isBlank(requestModel.getItemId()),"invalid itemId");
        Preconditions.checkArgument(requestModel.getQuantity() == 0,"invalid quantity");

        Cart cart = new Cart(requestModel);

        if(requestModel.getId() != null)
            cart.setId(requestModel.getId());
        else
            cart.setId(RandomUtil.generateSecureRandomString(32, RandomUtil.RandomModeType.ALPHANUMERIC));

        cart.setStatus(EntityStatus.ACTIVE);

        return saveCart(cart);

    }

    @Override
    public CollectionResponse<Cart> getByAccountId(String accountId, int limit, String cursorString) throws Exception{

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Cart> query = ofy().load().type(Cart.class);

        if (!ObjUtil.isBlank(accountId))
            query = query.filter("accountId", accountId);


        return fetchCursorQuery(query, limit, cursorString);
    }

    @Override
    public List<Cart> updateCarts(List<CartRequestModel> requestModelList, String accountId) throws Exception{

        Iterator iterator = requestModelList.iterator();
        List<Cart> carts = new ArrayList<>();

        while (iterator.hasNext()){
            CartRequestModel cartRequestModel = (CartRequestModel) iterator.next();
            if(cartRequestModel.getAccountId() == null)
                cartRequestModel.setAccountId(accountId);

            Cart cart = new Cart(cartRequestModel);
            updateCart(cart);
            carts.add(cart);
        }

        return carts;

    }

    @Override
    public Cart updateCart(Cart cart) throws Exception{


        Preconditions.checkArgument(ObjUtil.isBlank(cart.getAccountId()),"invalid accountid");
        Preconditions.checkArgument(ObjUtil.isBlank(cart.getContactId()),"invalid contactId");
        Preconditions.checkArgument(ObjUtil.isBlank(cart.getItemId()),"invalid itemId");
        Preconditions.checkArgument(cart.getQuantity() == 0,"invalid quantity");

        return saveCart(cart);

    }

    @Override
    public Cart deleteCart(Cart cart) throws Exception{
        return delete(cart) ? cart : null;
    }

    @Override
    public Cart saveCart(Cart cart) throws Exception{
        return save(cart) != null ? cart : null;
    }
}
