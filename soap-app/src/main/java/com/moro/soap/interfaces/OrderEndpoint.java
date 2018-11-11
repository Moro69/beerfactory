package com.moro.soap.interfaces;

import com.moro.model.Order;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Collection;

@WebService(targetNamespace = "http://moro.com/soap/ordersoapservice")
@SOAPBinding
public interface OrderEndpoint {
    /**
     * Get all orders.
     *
     * @return collection of orders.
     */
    @WebMethod
    Collection<Order> getAllOrders();

    /**
     * Get orders by date.
     *
     * @param fromDate beginning date.
     * @param toDate   ending date.
     * @return collection of orders.
     */
    @WebMethod
    Collection<Order> getOrdersByDate(@WebParam(name = "fromDate") String fromDate,
                                            @WebParam(name = "toDate") String toDate);

    /**
     * Get order by id.
     *
     * @param orderId order id.
     * @return order.
     */
    @WebMethod
    Order getOrderById(@WebParam(name = "orderId") Integer orderId);

    /**
     * Add new order.
     *
     * @param order new order.
     * @return added order with id.
     */
    @WebMethod
    Order addOrder(@WebParam(name = "order") Order order);

    /**
     * Delete order by id.
     *
     * @param orderId order id.
     */
    @WebMethod
    void deleteOrderById(@WebParam(name = "orderId") Integer orderId);
}
