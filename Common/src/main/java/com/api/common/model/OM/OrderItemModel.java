package com.api.common.model.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 24/06/18.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 8103509625978224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("price")
    private String price;

    @JsonProperty("salePrice")
    private String salePrice;

    @JsonProperty("offer")
    private String offer;

    @JsonProperty("discount")
    private int discount;

    @JsonProperty("quantity")
    private int quantity;


}
