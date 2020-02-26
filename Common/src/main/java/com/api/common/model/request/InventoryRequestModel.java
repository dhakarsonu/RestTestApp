package com.api.common.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by sonudhakar on 09/09/17.
 */
@Data
public class InventoryRequestModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("categoryId")
    private String categoryId;

    @JsonProperty("price")
    private String price;

    @JsonProperty("salePrice")
    private String salePrice;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("name")
    private String name;

    @JsonProperty("barCode")
    private String barCode;

    @JsonProperty("QRCode")
    private String QRCode;

    @JsonProperty("itemCode")
    private String itemCode;

    @JsonProperty("photoId")
    private String photoId;

    @JsonProperty("brandName")
    private String brandName;

    @JsonProperty("warrenty")
    private String warrenty;

    @JsonProperty("description")
    private String description;

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("gst")
    private int gst;

    @JsonProperty("cgst")
    private int cgst;

    @JsonProperty("sgst")
    private int sgst;

    @JsonProperty("offer")
    private String offer;

    @JsonProperty("discount")
    private int discount;

}
