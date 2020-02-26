package com.mncm.daoimpl;

import com.api.common.entity.IM.Product;
import com.api.common.entity.OM.OrderItem;
import com.api.common.entity.common.PaymentMethod;
import com.api.common.enums.EntityStatus;
import com.api.common.enums.OrderType;
import com.api.common.enums.PaymentMode;
import com.api.common.enums.ProcessStatus;
import com.api.common.model.OM.OrderItemModel;
import com.api.common.model.OM.PaymentMethodModel;
import com.api.common.model.request.OrderRequestModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.model.response.OrderResponseModel;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.OrderDao;
import com.api.common.entity.OM.Order;
import com.api.common.model.OM.OrderModel;
import com.mncm.dao.OrderItemDao;
import com.mncm.dao.PaymentMethodDao;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by sonudhakar on 28/10/17.
 */
@Slf4j
public class OrderDaoImpl extends OfyService implements OrderDao{

    static final OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Override
    public Order get(String id) throws Exception{
        return get(Order.class,id);
    }

    @Override
    public CollectionResponse<Order> getByContactId(String contactId, OrderType orderType, int limit, String cursorString) throws Exception{

        if (contactId == null)
            return null;

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Order> query = ofy().load().type(Order.class);

        if (!ObjUtil.isBlank(contactId))
            query = query.filter("contactId", contactId);

        if(orderType != null)
            query = query.filter("type", orderType);


        return fetchCursorQuery(query, limit, cursorString);

    }

    @Override
    public CollectionResponse<Order> getByPhone(String phone, OrderType orderType, int limit, String cursorString) throws Exception{

        if (phone == null)
            return null;

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Order> query = ofy().load().type(Order.class);

        if (!ObjUtil.isBlank(phone))
            query = query.filter("phone", phone);

        if(orderType != null)
            query = query.filter("type", orderType);


        return fetchCursorQuery(query, limit, cursorString);

    }

    @Override
    public CollectionResponse<Order> getByAccountId(String accountId, OrderType orderType, int limit, String cursorString) throws Exception{

        if (accountId == null)
            return null;

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<Order> query = ofy().load().type(Order.class);

        if (!ObjUtil.isBlank(accountId))
            query = query.filter("accountId", accountId);

        if(orderType != null)
            query = query.filter("type", orderType);


        return fetchCursorQuery(query, limit, cursorString);

    }

    @Override
    public OrderResponseModel createOrder(OrderRequestModel orderRequestModel) throws Exception{

        Preconditions.checkArgument(orderRequestModel == null,"Invalid payload");

        OrderModel orderModel = new OrderModel(orderRequestModel);
        if(orderModel.getId() == null)
            orderModel.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));


        List<Object> itemList = getLinkedItem(orderRequestModel.getLinkedItem());

        //Persisting Payment Method
        PaymentMethodModel paymentMethodModel = updatePaymentMethod(new PaymentMethodModel(),itemList);
        paymentMethodModel.setOrderId(orderModel.getId());

        PaymentMethodDao paymentMethodDao = new PaymentMethodDaoImpl();
        PaymentMethod paymentMethod = paymentMethodDao.createPaymentMethod(paymentMethodModel);


        //Persisting OrderItems

        List<OrderItemModel> orderItemModelList = orderRequestModel.getLinkedItem();
        Iterator iterator  = orderItemModelList.iterator();
        List<OrderItem> orderItemList = new ArrayList<>();

        while(iterator.hasNext()){
            OrderItemModel orderItemModel = (OrderItemModel) iterator.next();
            orderItemModel.setOrderId(orderModel.getId());
            Product product = new InventoryDaoImpl().get(orderItemModel.getItemId());
            orderItemModel.setDiscount(product.getDiscount());
            orderItemModel.setOffer(product.getOffer());
            orderItemModel.setPrice(product.getPrice());
            orderItemModel.setSalePrice(product.getSalePrice());
            OrderItem orderItem = orderItemDao.createOrderItem(orderItemModel);
            orderItemList.add(orderItem);
        }

        //Persisting Order
        Order order = new Order(orderModel);

        order = saveOrder(order);

        OrderResponseModel orderResponseModel = new OrderResponseModel();
        orderResponseModel.setOrder(order);
        orderResponseModel.setOrderItems(orderItemList);
        orderResponseModel.setPaymentMethod(paymentMethod);

        return orderResponseModel;
    }

    @Override
    public OrderResponseModel addItems(List<OrderItemModel> orderItemModelList,String orderId, String accountId) throws Exception{

        Order order = get(orderId);

        System.out.println(order);
        System.out.println(accountId);

        Preconditions.checkArgument(order == null,"Order does not exist");
        Preconditions.checkArgument(!order.getAccountId().equals(accountId),"Order does not belongs to the account");

        //Persisting OrderItems

        Iterator iterator  = orderItemModelList.iterator();
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        List<OrderItem> orderItemList = new ArrayList<>();

        while(iterator.hasNext()){
            OrderItemModel orderItemModel = (OrderItemModel) iterator.next();
            orderItemModel.setOrderId(orderId);
            Product product = new InventoryDaoImpl().get(orderItemModel.getItemId());
            orderItemModel.setDiscount(product.getDiscount());
            orderItemModel.setOffer(product.getOffer());
            orderItemModel.setPrice(product.getPrice());
            orderItemModel.setSalePrice(product.getSalePrice());
            OrderItem orderItem = orderItemDao.createOrderItem(orderItemModel);
            orderItemList.add(orderItem);
        }

        List<Object> itemList = getLinkedItem(orderItemModelList);

        PaymentMethodDao paymentMethodDao = new PaymentMethodDaoImpl();
        PaymentMethod paymentMethod = paymentMethodDao.getByOrderId(orderId);
        PaymentMethodModel paymentMethodModel = new PaymentMethodModel(paymentMethod);
        paymentMethodModel = updatePaymentMethod(paymentMethodModel,itemList);
        paymentMethod = new PaymentMethod(paymentMethodModel);
        paymentMethod = paymentMethodDao.savePaymentMethod(paymentMethod);

        OrderResponseModel orderResponseModel = new OrderResponseModel();
        orderResponseModel.setOrder(order);
        orderResponseModel.setOrderItems(orderItemList);

        return orderResponseModel;
    }

    @Override
    public Order saveOrder(Order order) throws Exception{
        if(order == null)
            return null;

        return save(order) != null ? order : null;
    }

    @Override
    public Order deleteOrder(String orderId) throws Exception{

        if(ObjUtil.isNullOrEmpty(orderId))
            return null;

        Order order = get(orderId);

        return delete(order) ? order : null;
    }

    private PaymentMethodModel updatePaymentMethod(PaymentMethodModel paymentMethodModel,List<Object> itemList){
        Iterator iterator = itemList.iterator();

        float basePrice = 0;
        float gst = 0;
        float sgst = 0;
        float cgst = 0;
        float amount = 0;
        while (iterator.hasNext()){
            Map<String,Float> item = (Map<String,Float>) iterator.next();

            basePrice = item.get("basePrice");
            basePrice += paymentMethodModel.getBasePrice();

            cgst = item.get("cgst");
            cgst += paymentMethodModel.getCgst();

            sgst = item.get("sgst");
            sgst += paymentMethodModel.getSgst();

            gst = item.get("gst");
            gst += paymentMethodModel.getGst();

            amount = item.get("amount");
            amount += paymentMethodModel.getAmount();
        }
        paymentMethodModel.setSgst(sgst);
        paymentMethodModel.setGst(gst);
        paymentMethodModel.setCgst(cgst);
        paymentMethodModel.setAmount(amount);
        paymentMethodModel.setBasePrice(basePrice);
        paymentMethodModel.setPaymentMode(PaymentMode.CASH);

        return paymentMethodModel;
    }

    private List<Object> getLinkedItem(List<OrderItemModel> items) throws Exception{

        Iterator iterator = items.iterator();
        List<Object> itemList = new ArrayList<>();

        while (iterator.hasNext()){
            OrderItemModel orderItemModel = (OrderItemModel) iterator.next();
            float quantity = (float) orderItemModel.getQuantity();
            String itemId = orderItemModel.getItemId();
            System.out.println(quantity);
            Product product = new InventoryDaoImpl().get(itemId);
            System.out.println(Integer.parseInt(product.getPrice()));
            System.out.println((quantity/1000));
            float basePrice = Integer.parseInt(product.getPrice()) * (quantity/1000);
            System.out.println(basePrice);
            float cgst      = (basePrice * product.getCgst())/100;
            float sgst      = (basePrice * product.getSgst())/100;
            float gst       = cgst + sgst;
            float amount    =  basePrice + gst;
            amount          = amount - ((amount * product.getDiscount())/100);
            Map<String,Float> item = new HashMap<>();
            item.put("basePrice",basePrice);
            item.put("cgst",cgst);
            item.put("sgst",sgst);
            item.put("gst",gst);
            item.put("amount",amount);
            itemList.add(item);
            System.out.println(item);
        }
        return itemList;
    }

}
