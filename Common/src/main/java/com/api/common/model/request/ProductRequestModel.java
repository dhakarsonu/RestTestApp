package com.api.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 17/03/18.
 */
@Data
@NoArgsConstructor
public class ProductRequestModel {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private int price;

    @JsonProperty("id")
    private String id;

}
