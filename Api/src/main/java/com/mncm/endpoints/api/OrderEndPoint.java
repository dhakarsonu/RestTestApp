package com.mncm.endpoints.api;

import com.api.common.entity.OM.Order;
import com.api.common.entity.OM.OrderItem;
import com.api.common.entity.common.LinkedProduct;
import com.api.common.entity.common.PaymentMethod;
import com.api.common.enums.ApiErrorCode;
import com.api.common.enums.OrderType;
import com.api.common.enums.ProcessStatus;
import com.api.common.exception.EntityException;
import com.api.common.model.OM.OrderItemModel;
import com.api.common.model.request.OrderRequestModel;
import com.api.common.model.response.CollectionResponse;
import com.api.common.model.response.OrderResponseModel;
import com.api.common.utils.ObjUtil;
import com.api.common.utils.Preconditions;
import com.mncm.dao.OrderDao;
import com.mncm.dao.OrderItemDao;
import com.mncm.dao.PaymentMethodDao;
import com.mncm.daoimpl.OrderDaoImpl;
import com.mncm.daoimpl.OrderItemDaoImpl;
import com.mncm.daoimpl.PaymentMethodDaoImpl;
import com.mncm.model.request.BaseApiRequest;
import com.mncm.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by sonudhakar on 03/09/17.
 */
@Slf4j
@Path("v1/order/account/{accountId}")
@Consumes(MediaType.APPLICATION_JSON)
public class OrderEndPoint {

    private final OrderDao orderDao = new OrderDaoImpl();
    private final PaymentMethodDao paymentMethodDao = new PaymentMethodDaoImpl();
    private final OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("accountId") String accountId, OrderRequestModel orderRequestModel){
        ApiResponse apiResponse = new ApiResponse();
        try{
            Preconditions.checkArgument(orderRequestModel == null,"Invalid Request");
            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"Invalid accountId");
            orderRequestModel.setAccountId(accountId);
            OrderResponseModel orderResponseModel = orderDao.createOrder(orderRequestModel);

            if(orderResponseModel != null){
                apiResponse.setOk(true);
                if(orderRequestModel.getType().equals(OrderType.DELIVERY))
                    apiResponse.add("order",orderResponseModel);
                else
                    apiResponse.add("order",orderResponseModel.getOrder());
            }

        } catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }

    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByAccountId(@PathParam("accountId") String accountId, @QueryParam("type") OrderType orderType, @Form BaseApiRequest apiRequest){
        ApiResponse apiResponse = new ApiResponse();
        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"Invalid accountId");

            CollectionResponse<Order> collectionResponse = orderDao.getByAccountId(accountId,orderType,apiRequest.getLimit(), apiRequest.getCursor());

            if(collectionResponse == null){
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "No order found");
            }

            List<Object> orderList = getOrderList(collectionResponse,orderType);
            apiResponse.add("order", orderList);
            apiResponse.add("cursor", collectionResponse.getCursor());

        } catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }


    @Path("/contact/{contactId}/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByContactId(@PathParam("accountId") String accountId,@PathParam("contactId") String contactId, @QueryParam("type") OrderType orderType, @Form BaseApiRequest apiRequest){
        ApiResponse apiResponse = new ApiResponse();
        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"Invalid accountId");
            Preconditions.checkArgument(ObjUtil.isBlank(contactId),"Invalid contactId");

            CollectionResponse<Order> collectionResponse = orderDao.getByContactId(contactId,orderType, apiRequest.getLimit(), apiRequest.getCursor());

            if(collectionResponse == null){
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "No order found");
            }

            List<Object> orderList = getOrderList(collectionResponse,orderType);

            apiResponse.add("order",orderList);


        } catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }

    @Path("/phone/{phoneNumber}/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByPhone(@PathParam("accountId") String accountId,@PathParam("phoneNumber") String phoneNumber, @QueryParam("type") OrderType orderType, @Form BaseApiRequest apiRequest){
        ApiResponse apiResponse = new ApiResponse();
        try{

            Preconditions.checkArgument(ObjUtil.isBlank(accountId),"Invalid accountId");
            Preconditions.checkArgument(ObjUtil.isBlank(phoneNumber),"Invalid phone number");

            CollectionResponse<Order> collectionResponse = orderDao.getByPhone(phoneNumber,orderType, apiRequest.getLimit(), apiRequest.getCursor());

            if(collectionResponse == null){
                throw new EntityException(ApiErrorCode.BAD_REQUEST, "No order found");
            }

            List<Object> orderList = getOrderList(collectionResponse,orderType);

            apiResponse.add("order",orderList);


        } catch(EntityException | IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }

    @Path("/order/{orderId}/update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("accountId") String accountId, @PathParam("orderId") String orderId, Map<String,String> payload){
        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(payload == null,"Invalid request payload");

            Preconditions.checkArgument(ObjUtil.isBlank(orderId),"orderId is not valid");

            Map<String,Object> response = new HashMap<>();

            String orderStatus = payload.get("orderStatus");

            if(orderStatus != null) {
                Order order = orderDao.get(orderId);
                order.setStatus(ProcessStatus.valueOf(orderStatus));
                order = orderDao.saveOrder(order);
                response.put("order",order);
            }

            String paymentStatus = payload.get("paymentStatus");

            if(paymentStatus != null){
                PaymentMethod paymentMethod = paymentMethodDao.getByOrderId(orderId);
                paymentMethod.setStatus(ProcessStatus.valueOf(paymentStatus));
                paymentMethod = paymentMethodDao.savePaymentMethod(paymentMethod);
                response.put("paymentMethod",paymentMethod);
            }

            String name = payload.get("name");
            if(name != null){
                Order order = orderDao.get(orderId);
                order.setName(name);
                order = orderDao.saveOrder(order);
                response.put("order",order);
            }

            long time = Long.parseLong(payload.get("time"));
            if(time != 0){
                int repeat = Integer.parseInt((payload.get("repeat")));
                Order order = orderDao.get(orderId);
                order.setTime(time);
                order.setRepeat(repeat);
                order = orderDao.saveOrder(order);
                response.put("order",order);
            }

            if(!ObjUtil.isNullOrEmpty(response)){
                apiResponse.setOk(true);
                apiResponse.add("data",response);
            }


        }catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }

    @Path("/order/{orderId}/item/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemInOrder(@PathParam("accountId") String accountId, @PathParam("orderId") String orderId,@QueryParam("type") String type, OrderRequestModel orderRequestModel){
        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(orderId),"orderId is not valid");

            Preconditions.checkArgument(orderRequestModel == null,"Invalid request payload");

            OrderResponseModel orderResponseModel = orderDao.addItems(orderRequestModel.getLinkedItem(),orderId,accountId);

            if(orderResponseModel != null){
                apiResponse.setOk(true);
                if(type != null && type.equals(OrderType.DELIVERY))
                    apiResponse.add("order",orderResponseModel);
                else
                    apiResponse.add("order",orderResponseModel.getOrder());
            }

        }catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }

    @Path("/order/{orderId}/delete")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("orderId") String orderId){

        ApiResponse apiResponse = new ApiResponse();

        try{

            Preconditions.checkArgument(ObjUtil.isBlank(orderId),"orderId is not valid");

            Order order = orderDao.deleteOrder(orderId);

            if(order != null){
                apiResponse.setOk(true);
                apiResponse.add("order",order);
            }

        }catch(IllegalArgumentException e){
            log.error(e.getMessage(), e);
            apiResponse.addError(ApiErrorCode.BAD_REQUEST, e.getMessage());
            return Response.status(400).entity(apiResponse).build();
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return Response.status(500).entity(apiResponse).build();
        }
        apiResponse.setOk(true);
        return Response.ok(apiResponse).build();
    }


    private List<Object> getOrderList(CollectionResponse<Order> collectionResponse, OrderType orderType){
        try{
            List<Object> orderList = new ArrayList<>();

            for(Order order : collectionResponse.getItems()){
                Map<String,Object> orderDetail = new HashMap<>();
                PaymentMethod paymentMethod = paymentMethodDao.getByOrderId(order.getId());
                List<OrderItem> orderItemList = orderItemDao.getAllByOrderId(order.getId());
                orderDetail.put("order",order);
                //if(orderType.equals(OrderType.DELIVERY)) {
                    orderDetail.put("paymentMethod", paymentMethod);
                //}
                orderDetail.put("orderItem",orderItemList);
                orderList.add(orderDetail);
            }
            return orderList;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }


}
