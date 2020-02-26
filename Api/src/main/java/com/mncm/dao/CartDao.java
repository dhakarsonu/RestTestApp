package com.mncm.dao;

import com.api.common.entity.common.Cart;
import com.api.common.model.request.CartRequestModel;
import com.api.common.model.response.CollectionResponse;

import java.util.List;

/**
 * Created by sonudhakar on 18/03/18.
 */
public interface CartDao {

    public Cart get(String id);

    public Cart getByItemId(String itemId)throws Exception;

    public Cart createCart(CartRequestModel requestModel) throws Exception;

    public CollectionResponse<Cart> getByAccountId(String accountId, int limit, String cursorString) throws Exception;

    public Cart deleteCart(Cart cart) throws Exception;

    public Cart saveCart(Cart cart) throws Exception;

    public Cart updateCart(Cart cart) throws Exception;

    public List<Cart> updateCarts(List<CartRequestModel> requestModelList, String accountId) throws Exception;
}
