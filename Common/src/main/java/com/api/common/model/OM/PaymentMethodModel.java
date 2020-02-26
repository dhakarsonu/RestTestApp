package com.api.common.model.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.entity.common.PaymentMethod;
import com.api.common.enums.PaymentMode;
import com.api.common.model.request.OrderRequestModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 23/09/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentMethodModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 8103509625951224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("paymentMode")
    private PaymentMode paymentMode;

    @JsonProperty("basePrice")
    private float basePrice;

    @JsonProperty("gst")
    private float gst;

    @JsonProperty("cgst")
    private float cgst;

    @JsonProperty("sgst")
    private float sgst;

    @JsonProperty("amount")
    private float amount;

    @JsonProperty("orderId")
    private String orderId;

    public PaymentMethodModel(PaymentMethod model){

        this.id = model.getId();
        this.paymentMode = model.getPaymentMode();
        this.orderId = model.getOrderId();
        this.basePrice = model.getBasePrice();
        this.gst = model.getGst();
        this.cgst = model.getCgst();
        this.sgst = model.getSgst();
        this.amount = model.getAmount();

    }
    public PaymentMethodModel(){

    }


}
