package com.api.common.model.request;

import com.api.common.enums.OrderType;
import com.api.common.model.OM.OrderItemModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by sonudhakar on 23/06/18.
 */
@Data
public class OrderRequestModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("linkedItem")
    private List<OrderItemModel> linkedItem;

    @JsonProperty("addressId")
    private String addressId;

    @JsonProperty("contactId")
    private String contactId;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("time")
    private long time;

    @JsonProperty("repeat")
    private int repeat;

    @JsonProperty("type")
    private OrderType type;

}
