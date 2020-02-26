package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.EntityStatus;
import com.api.common.model.request.CartRequestModel;
import com.googlecode.objectify.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sonudhakar on 18/03/18.
 */
@Data
@Entity
@NoArgsConstructor
@Cache
@EqualsAndHashCode(callSuper = true)
public class Cart extends AbstractBaseEntity implements Serializable{

    private static final long serialVersionUID = 1712139888615129354L;

    @Index
    private String accountId;


    @Index
    private String contactId;

    @Index
    private String itemId;

    @Unindex
    private int quantity;

    @Unindex
    private EntityStatus status;

    public Cart(String id,String accountId,String contactId, String itemId, int quantity){
        this.id         = id;
        this.accountId  = accountId;
        this.contactId  = contactId;
        this.itemId     = itemId;
        this.quantity   = quantity;
    }

    public Cart(CartRequestModel requestModel){
        this.id         = requestModel.getId();
        this.accountId  = requestModel.getAccountId();
        this.contactId  = requestModel.getContactId();
        this.itemId     = requestModel.getItemId();
        this.quantity   = requestModel.getQuantity();
    }

    @OnSave
    public void defaultAccount(){

        if(this.status == null){
            this.status = EntityStatus.ACTIVE;
        }

    }
}
