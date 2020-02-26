package com.api.common.model.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.model.request.ProductRequestModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 17/03/18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LinkedProductModel extends AbstractBaseEntity implements Serializable{
    private static final long serialVersionUID = 8104509625951224949L;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private int price;

    public LinkedProductModel(String accountId, String productId, int quantity, String id,int price){

        this.accountId = accountId;
        this.productId = productId;
        this.quantity  = quantity;
        this.id        = id;
        this.price     = price;
    }

    public LinkedProductModel(ProductRequestModel productRequestModel){
        this.accountId = productRequestModel.getAccountId();
        this.productId = productRequestModel.getProductId();
        this.quantity  = productRequestModel.getQuantity();
        this.id        = productRequestModel.getId();
        this.price     = productRequestModel.getPrice();
    }
}
