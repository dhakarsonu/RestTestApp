package com.mncm.dao;

import com.api.common.entity.common.PaymentMethod;
import com.api.common.model.OM.PaymentMethodModel;
import com.api.common.services.objectify.OfyService;

/**
 * Created by sonudhakar on 24/06/18.
 */
public interface PaymentMethodDao{
    public PaymentMethod get(String id) throws Exception;

    public PaymentMethod getByOrderId(String orderId) throws Exception;

    public PaymentMethod createPaymentMethod(PaymentMethodModel paymentMethodModel) throws Exception;

    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) throws Exception;

    public PaymentMethod deletePaymentMethod(String id) throws Exception;
}
