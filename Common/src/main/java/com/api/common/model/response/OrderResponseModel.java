package com.api.common.model.response;

import com.api.common.entity.OM.Order;
import com.api.common.entity.OM.OrderItem;
import com.api.common.entity.common.PaymentMethod;
import lombok.Data;

import java.util.List;

/**
 * Created by sonudhakar on 24/06/18.
 */
@Data
public class OrderResponseModel {

    private Order order;
    private List<OrderItem> orderItems;
    private PaymentMethod paymentMethod;

}
