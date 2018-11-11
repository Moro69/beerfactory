package com.moro.soap.implementation;

import com.moro.model.Order;
import com.moro.service.interfaces.OrderService;
import com.moro.soap.interfaces.OrderEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.Collection;

@WebService(endpointInterface = "com.moro.soap.interfaces.OrderEndpoint", serviceName = "orderProcess",
            portName = "OrderProcessPort",  targetNamespace = "http://moro.com/soap/ordersoapservice")
public class OrderEndpointImpl implements OrderEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEndpointImpl.class);

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public Collection<Order> getAllOrders() {
        LOGGER.debug("getAllOrders()");

        Collection<Order> orders = orderService.getAllOrders();
        return orders;
    }

    @Override
    public Collection<Order> getOrdersByDate(String fromDate, String toDate) {
        LOGGER.debug("getOrdersByDate({},{})", fromDate, toDate);

        Collection<Order> orders =
                orderService.getOrdersByDate(fromDate, toDate);

        return orders;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        LOGGER.debug("getOrderById({})", orderId);
        Order order = orderService.getOrderById(orderId);

        return order;
    }

    @Override
    public Order addOrder(Order order) {
        LOGGER.debug("addOrder({})", order);

        Order newOrder = orderService.addOrder(order);

        return newOrder;
    }

    @Override
    public void deleteOrderById(Integer orderId) {
        LOGGER.debug("deleteOrderById({})", orderId);

        orderService.deleteOrderById(orderId);
    }
}
