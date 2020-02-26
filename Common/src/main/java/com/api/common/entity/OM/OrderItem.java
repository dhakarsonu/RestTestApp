package com.api.common.entity.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.model.OM.OrderItemModel;
import com.api.common.utils.ObjUtil;
import com.googlecode.objectify.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 24/06/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends AbstractBaseEntity {

    private static final long serialVersionUID = 1712149888615129334L;

    @Index
    private String orderId;

    @Index
    private String offer;

    @Index
    private String itemId;

    @Unindex
    private String price;

    @Unindex
    private String salePrice;

    @Unindex
    private int discount;

    @Unindex
    private int quantity;

    public OrderItem(OrderItemModel orderItemModel){
        this.id = orderItemModel.getId();
        this.orderId = orderItemModel.getOrderId();
        this.itemId = orderItemModel.getItemId();
        this.price = orderItemModel.getPrice();
        this.discount = orderItemModel.getDiscount();
        this.salePrice = orderItemModel.getSalePrice();
        this.offer = orderItemModel.getOffer();
        this.quantity = orderItemModel.getQuantity();
    }

    @OnSave
    public void saveDefualtEnity(){
        if(this.offer == null)
            this.offer = "";
    }
}
