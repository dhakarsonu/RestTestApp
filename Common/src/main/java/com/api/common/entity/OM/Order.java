package com.api.common.entity.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.EntityStatus;
import com.api.common.enums.OrderType;
import com.api.common.enums.PaymentMode;
import com.api.common.enums.ProcessStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.*;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.OM.OrderModel;
import com.api.common.model.IM.ProductModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 28/10/17.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class Order extends AbstractBaseEntity{

    private static final long serialVersionUID = 1712149888615129334L;

    @Index
    private String linkedAddressId;

    @Index
    private String accountId;

    @Index
    private long time;

    @Unindex
    private int repeat;

    @Index
    private String contactId;

    @Index
    private String phone;

    @Index
    private OrderType type;

    @Unindex
    private String name;

    @Unindex
    private ProcessStatus status;

    public Order(OrderModel model){
        this.id = model.getId();
        this.linkedAddressId = model.getLinkedAddressId();
        this.accountId = model.getAccountId();
        this.contactId = model.getContactId();
        this.status = model.getStatus();
        this.phone  = model.getPhone();
        this.name = model.getName();
        this.type = model.getType();
        this.time = model.getTime();
        this.repeat = model.getRepeat();
    }

    @OnSave
    public void SaveDefaultEntity(){
        if(this.status == null)
            this.status = ProcessStatus.PROCESSING;
    }
}
