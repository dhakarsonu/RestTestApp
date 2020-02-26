package com.api.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Data
public class CartRequestModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("contactId")
    private String contactId;

    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("accountId")
    private String accountId;

}
