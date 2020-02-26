package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.model.common.LinkedProductModel;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 17/03/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class LinkedProduct extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712133488584129364L;

    @Index
    private String accountId;

    @Unindex
    private String productId;

    @Unindex
    private int quantity;

    public LinkedProduct(LinkedProductModel linkedProductModel){
        this.accountId = linkedProductModel.getAccountId();
        this.productId = linkedProductModel.getProductId();
        this.quantity  = linkedProductModel.getQuantity();
        this.id        = linkedProductModel.getId();
    }
}
