package com.mncm.dao;

import com.api.common.entity.OM.Order;
import com.api.common.enums.OrderType;
import com.api.common.model.OM.OrderItemModel;
import com.api.common.model.OM.OrderModel;
import com.api.common.model.request.OrderRequestModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.model.response.OrderResponseModel;

import java.util.List;

/**
 * Created by sonudhakar on 28/10/17.
 */
public interface OrderDao {

    public Order get(String id) throws Exception;

    public CollectionResponse<Order> getByContactId(String contactId, OrderType orderType, int limit, String cursorString) throws Exception;

    public CollectionResponse<Order> getByAccountId(String accountId, OrderType orderType, int limit, String cursorString) throws Exception;

    public OrderResponseModel createOrder(OrderRequestModel orderRequestModel) throws Exception;

    public Order saveOrder(Order order) throws Exception;

    public Order deleteOrder(String orderId) throws Exception;

    public CollectionResponse<Order> getByPhone(String phone, OrderType orderType, int limit, String cursorString) throws Exception;

    public OrderResponseModel addItems(List<OrderItemModel> orderItemModelList, String orderId, String accountId) throws Exception;
}
