package com.api.common.model.IM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.model.request.InventoryRequestModel;
import com.api.common.utils.ObjUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 09/09/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductModel extends AbstractBaseEntity implements Serializable{

    private static final long serialVersionUID = 8103509625951224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("applicationId")
    private String applicationId;

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

    @JsonProperty("description")
    private String description;

    public ProductModel(InventoryRequestModel requestModel){

        if(!ObjUtil.isNullOrEmpty(requestModel.getId()))
            this.id = requestModel.getId();

        if(!ObjUtil.isNullOrEmpty(requestModel.getCategoryId()))
            this.categoryId = requestModel.getCategoryId();

        if(!ObjUtil.isNullOrEmpty(requestModel.getPrice()))
            this.price = requestModel.getPrice();


        this.quantity = requestModel.getQuantity();

        if(!ObjUtil.isNullOrEmpty(requestModel.getName()))
            this.name = requestModel.getName();

        if(!ObjUtil.isNullOrEmpty(requestModel.getBarCode()))
            this.barCode = requestModel.getBarCode();

        if(!ObjUtil.isNullOrEmpty(requestModel.getQRCode()))
            this.QRCode = requestModel.getQRCode();

        if(!ObjUtil.isNullOrEmpty(requestModel.getItemCode()))
            this.itemCode = requestModel.getItemCode();

        if(!ObjUtil.isNullOrEmpty(requestModel.getPhotoId()))
            this.photoId = requestModel.getPhotoId();

        if(!ObjUtil.isNullOrEmpty(requestModel.getBrandName()))
            this.brandName = requestModel.getBrandName();

        if(!ObjUtil.isNullOrEmpty(requestModel.getWarrenty()))
            this.warrenty = requestModel.getWarrenty();

        if(!ObjUtil.isNullOrEmpty(requestModel.getDescription()))
            this.description = requestModel.getDescription();

        if(!ObjUtil.isNullOrEmpty(requestModel.getApplicationId()))
            this.applicationId = requestModel.getApplicationId();


        this.gst = requestModel.getGst();

        this.cgst = requestModel.getCgst();

        this.sgst = requestModel.getSgst();

        this.discount = requestModel.getDiscount();

        if(!ObjUtil.isNullOrEmpty(requestModel.getOffer()))
            this.offer = requestModel.getOffer();

        if(!ObjUtil.isNullOrEmpty(requestModel.getSalePrice()))
            this.salePrice = requestModel.getSalePrice();
    }

}
