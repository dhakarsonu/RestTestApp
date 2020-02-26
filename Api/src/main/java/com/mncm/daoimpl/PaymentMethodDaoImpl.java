package com.mncm.daoimpl;

import com.api.common.entity.common.PaymentMethod;
import com.api.common.model.OM.PaymentMethodModel;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.RandomUtil;
import com.mncm.dao.PaymentMethodDao;

/**
 * Created by sonudhakar on 24/06/18.
 */
public class PaymentMethodDaoImpl extends OfyService implements PaymentMethodDao{

    @Override
    public PaymentMethod get(String id) throws Exception{
        return get(PaymentMethod.class,id);
    }

    @Override
    public PaymentMethod getByOrderId(String orderId) throws Exception{
        if (orderId == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(PaymentMethod.class).filter("orderId", orderId).first().now();
    }

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethodModel paymentMethodModel) throws Exception{

        if(paymentMethodModel == null)
            return null;

        PaymentMethod paymentMethod = new PaymentMethod(paymentMethodModel);

        if(paymentMethod.getId() == null)
            paymentMethod.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));

        return savePaymentMethod(paymentMethod);

    }

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) throws Exception{
        if(paymentMethod == null)
            return null;

        return save(paymentMethod) != null ? paymentMethod : null;
    }

    @Override
    public PaymentMethod deletePaymentMethod(String id) throws Exception{

        if(ObjUtil.isNullOrEmpty(id))
            return null;

        PaymentMethod paymentMethod = get(id);

        return delete(paymentMethod) ? paymentMethod : null;
    }
}
