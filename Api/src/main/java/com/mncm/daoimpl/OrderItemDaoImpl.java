package com.mncm.daoimpl;

import com.api.common.entity.OM.OrderItem;
import com.api.common.entity.common.LinkedProduct;
import com.api.common.model.OM.OrderItemModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.services.objectify.OfyService;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.api.common.utils.RandomUtil;
import com.googlecode.objectify.cmd.Query;
import com.mncm.dao.OrderItemDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by sonudhakar on 24/06/18.
 */
@Slf4j
public class OrderItemDaoImpl extends OfyService implements OrderItemDao{

    @Override
    public OrderItem get(String id) throws Exception{
        return get(OrderItem.class,id);
    }

    @Override
    public CollectionResponse<OrderItem> getByOrderId(String orderId, int limit, String cursorString) throws Exception{

        if (orderId == null)
            return null;

        if (limit <= 0)
            limit = 10;
        else if (limit > 30)
            limit = 30;

        Query<OrderItem> query = ofy().load().type(OrderItem.class);

        if (!ObjUtil.isBlank(orderId))
            query = query.filter("orderId", orderId);


        return fetchCursorQuery(query, limit, cursorString);

    }

    @Override
    public List<OrderItem> getAllByOrderId(String orderId) throws Exception{

        if (orderId == null)
            return null;

        Query<OrderItem> query = ofy().load().type(OrderItem.class);

        if (!ObjUtil.isBlank(orderId))
            query = query.filter("orderId", orderId);


        return getEntitiesByFilter(OrderItem.class,"orderId",orderId);

    }

    @Override
    public OrderItem getByItemId(String itemId) throws Exception{

        if (itemId == null)
            return null;

        return ofy().load().type(OrderItem.class).filter("itemId", itemId).first().now();

    }

    @Override
    public OrderItem getByOffer(String offer) throws Exception{
        if (offer == null)
            return null;

        //TODO it can be cached
        return ofy().load().type(OrderItem.class).filter("offer", offer).first().now();
    }

    @Override
    public OrderItem createOrderItem(OrderItemModel orderItemModel) throws Exception{

        if(orderItemModel == null)
            return null;

        OrderItem orderItem = new OrderItem(orderItemModel);

        if(orderItem.getId() == null)
            orderItem.setId(RandomUtil.generateSecureRandomString(32,RandomUtil.RandomModeType.ALPHANUMERIC));

        return saveOrderItem(orderItem);

    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) throws Exception{
        if(orderItem == null)
            return null;

        return save(orderItem) != null ? orderItem : null;
    }

    @Override
    public OrderItem deleteOrderItem(String id) throws Exception{

        if(ObjUtil.isNullOrEmpty(id))
            return null;

        OrderItem orderItem = get(id);

        return delete(orderItem) ? orderItem : null;
    }
}
