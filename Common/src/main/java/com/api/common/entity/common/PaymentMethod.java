package com.api.common.entity.common;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.EntityStatus;
import com.api.common.enums.PaymentMode;
import com.api.common.enums.ProcessStatus;
import com.api.common.model.OM.PaymentMethodModel;
import com.googlecode.objectify.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by sonudhakar on 24/06/18.
 */
@Data
@NoArgsConstructor
@Cache
@Entity
@EqualsAndHashCode(callSuper = true)
public class PaymentMethod extends AbstractBaseEntity {

    private static final long serialVersionUID = 1712139888515129334L;

    @Index
    private PaymentMode paymentMode;

    @Index
    private String orderId;

    @Unindex
    private float basePrice;

    @Unindex
    private float gst;

    @Unindex
    private float cgst;

    @Unindex
    private float sgst;

    @Unindex
    private float amount;

    @Unindex
    private ProcessStatus status;


    public PaymentMethod(PaymentMethodModel model){

        this.id = model.getId();
        this.paymentMode = model.getPaymentMode();
        this.orderId = model.getOrderId();
        this.basePrice = model.getBasePrice();
        this.gst = model.getGst();
        this.cgst = model.getCgst();
        this.sgst = model.getSgst();
        this.amount = model.getAmount();

    }

    @OnSave
    public void SaveDefaultEntity(){
        if(this.status == null)
            this.status = ProcessStatus.PENDING;
    }

}
