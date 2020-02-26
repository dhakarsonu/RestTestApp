package com.api.common.model.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.EntityStatus;
import com.api.common.enums.OrderType;
import com.api.common.enums.PaymentMode;
import com.api.common.enums.ProcessStatus;
import com.api.common.model.request.OrderRequestModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.api.common.model.contact.ContactMethodModel;
import com.api.common.model.IM.ProductModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by sonudhakar on 23/09/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderModel  extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 8103509625978224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("linkedAddressId")
    private String linkedAddressId;

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("contactId")
    private String contactId;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private OrderType type;

    @JsonProperty("time")
    private long time;

    @JsonProperty("repeat")
    private int repeat;

    @JsonProperty("status")
    private ProcessStatus status;


    public OrderModel(OrderRequestModel model){
        this.id = model.getId();
        this.linkedAddressId = model.getAddressId();
        this.accountId = model.getAccountId();
        this.contactId = model.getContactId();
        this.phone     = model.getPhone();
        this.time = model.getTime();
        this.repeat = model.getRepeat();
        this.name = model.getName();
        this.type = model.getType();
    }
}
