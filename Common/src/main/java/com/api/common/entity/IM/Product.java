package com.api.common.entity.IM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.EntityStatus;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.annotation.*;
import com.api.common.model.IM.ProductModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 09/09/17.
 */

@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712133488583129364L;


    @Index
    private String categoryId;

    @Unindex
    private String price;

    @Unindex
    private int quantity;

    @Unindex
    private String salePrice;

    @Unindex
    private String name;

    @Index
    private String barCode;

    @Index
    private String QRCode;

    @Index
    private String applicationId;

    @Index
    private String itemCode;

    @Unindex
    private String photoId;

    @Index
    private String brandName;

    @Unindex
    private String warrenty;

    @Unindex
    private String description;

    @Unindex
    private int gst;

    @Unindex
    private int cgst;

    @Unindex
    private int sgst;

    @Unindex
    private String offer;

    @Unindex
    private int discount;


    public Product(ProductModel inventoryModel){

        this.id = inventoryModel.getId();

        this.categoryId = inventoryModel.getCategoryId();

        this.price = inventoryModel.getPrice();

        this.quantity = inventoryModel.getQuantity();

        this.name = inventoryModel.getName();

        this.barCode = inventoryModel.getBarCode();

        this.itemCode = inventoryModel.getItemCode();

        this.photoId = inventoryModel.getPhotoId();

        this.brandName = inventoryModel.getBrandName();

        this.warrenty = inventoryModel.getWarrenty();

        this.description = inventoryModel.getDescription();

        this.QRCode = inventoryModel.getQRCode();

        this.applicationId = inventoryModel.getApplicationId();

        this.salePrice = inventoryModel.getSalePrice();

        this.gst = inventoryModel.getGst();

        this.cgst = inventoryModel.getCgst();

        this.sgst = inventoryModel.getSgst();

        this.offer = inventoryModel.getOffer();

        this.discount = inventoryModel.getDiscount();

    }

    @OnSave
    public void defaultSave(){

        if(this.barCode == null)
            this.barCode = "";
        if(this.QRCode == null)
            this.QRCode = "";
        if(this.itemCode == null)
            this.itemCode = RandomUtil.generateSecureRandomString(10, RandomUtil.RandomModeType.ALPHANUMERIC);
        if(this.photoId == null)
            this.photoId = "";
        if(this.brandName == null)
            this.brandName = "";
        if(this.warrenty == null)
            this.warrenty = "";
        if(this.description == null)
            this.description = "";
    }
}
