package com.mncm.dao;

import com.api.common.entity.OM.OrderItem;
import com.api.common.model.OM.OrderItemModel;
import com.api.common.model.response.CollectionResponse;

import java.util.List;

/**
 * Created by sonudhakar on 24/06/18.
 */
public interface OrderItemDao {

    public OrderItem get(String id) throws Exception;

    public OrderItem getByOffer(String offer) throws Exception;

    public OrderItem createOrderItem(OrderItemModel orderItemModel) throws Exception;

    public OrderItem saveOrderItem(OrderItem orderItem) throws Exception;

    public OrderItem deleteOrderItem(String id) throws Exception;

    public CollectionResponse<OrderItem> getByOrderId(String orderId, int limit, String cursorString) throws Exception;

    public List<OrderItem> getAllByOrderId(String orderId) throws Exception;

    public OrderItem getByItemId(String itemId) throws Exception;
}
