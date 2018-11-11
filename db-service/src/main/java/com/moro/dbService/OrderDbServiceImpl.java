package com.moro.dbService;

import com.moro.dao.interfaces.OrderDao;
import com.moro.model.Order;
import com.moro.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class OrderDbServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDbServiceImpl.class);

    private OrderDao orderDao;

    @Autowired
    public OrderDbServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Collection<Order> getAllOrders() {
        LOGGER.debug("getAllOrders()");

        return orderDao.getAllOrders();
    }

    @Override
    public Collection<Order> getOrdersByDate(String fromDate, String toDate) {
        LOGGER.debug("getOrdersByDate(from {} to {})", fromDate, toDate);

        return orderDao.getOrdersByDate(fromDate, toDate);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        LOGGER.debug("getOrderById({})", orderId);

        return orderDao.getOrderById(orderId);
    }

    @Override
    public Order addOrder(Order order) {
        LOGGER.debug("addOrder({})", order);

        return orderDao.addOrder(order);
    }

    @Override
    public void deleteOrderById(Integer orderId) {
        LOGGER.debug("deleteOrderById({})", orderId);

        orderDao.deleteOrderById(orderId);
    }
}
