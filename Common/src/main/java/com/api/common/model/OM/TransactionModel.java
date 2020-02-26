package com.api.common.model.OM;

import com.api.common.entity.AbstractBaseEntity;
import com.api.common.enums.PaymentMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sonudhakar on 23/09/17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionModel extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 8103509625951224949L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("bankName")
    private String bankName;

    @JsonProperty("contactId")
    private String contactId;

    @JsonProperty("cardNumber")
    private String cardNumber;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("paymentBrandName")
    private String paymentBrandName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("time")
    private String time;

    @JsonProperty("mode")
    private PaymentMode mode;

    @JsonProperty("orderId")
    private String orderId;


}
