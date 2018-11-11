package com.moro.dao.interfaces;

import com.moro.model.Order;

import java.util.Collection;

/**
 * Order DAO interface.
 */
public interface OrderDao {

    /**
     * Get all orders.
     *
     * @return collection of orders.
     */
    Collection<Order> getAllOrders();

    /**
     * Get orders by date.
     *
     * @param fromDate beginning date.
     * @param toDate ending date.
     * @return collection of orders.
     */
    Collection<Order> getOrdersByDate(String fromDate, String toDate);

    /**
     * Get order by id.
     *
     * @param orderId order id.
     * @return order.
     */
    Order getOrderById(Integer orderId);

    /**
     * Add new order.
     *
     * @param order new order.
     * @return added order with id.
     */
    Order addOrder(Order order);

    /**
     * Delete order by id.
     *
     * @param orderId order id.
     */
    void deleteOrderById(Integer orderId);

}
