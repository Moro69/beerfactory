package com.moro.dao.test;

import com.moro.dao.interfaces.OrderDao;
import com.moro.model.Order;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-db-spring.xml",
        "classpath:test-dao.xml", "classpath:dao.xml"})
@Rollback
@Transactional
public class OrderDaoImplTest {

    @Autowired
    private OrderDao orderDao;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getAllOrders() {
        Collection<Order> orders =
                orderDao.getAllOrders();

        Assert.assertFalse(orders.isEmpty());
    }

    @Test
    public void getOrdersByDate() {
        Collection<Order> orders =
                orderDao.getOrdersByDate("2017-05-23", "2018-06-23");

        Assert.assertEquals(2, orders.size());

    }

    @Test
    public void getOrderById() {
        Order order = orderDao.getOrderById(1);

        Assert.assertNotNull(order);
        Assert.assertEquals(1, (int) order.getOrderId());
        Assert.assertEquals("2017-06-23", order.getOrderDate().toString());
        Assert.assertEquals(4, (int) order.getBeerId());
        Assert.assertEquals(25, order.getBeerQuantity());
        Assert.assertEquals(4250, order.getOrderPrice());
    }

    @Test
    public void addOrder() {
        Collection<Order> orders =
                orderDao.getAllOrders();
        int sizeBefore = orders.size();

        Order order = new Order("2018-01-02",
                1, 10, 3000);

        Order newOrder = orderDao.addOrder(order);

        Assert.assertNotNull(newOrder);
        Assert.assertEquals(sizeBefore  + 1, (int) newOrder.getOrderId());
        Assert.assertTrue(newOrder.getOrderDate().equals(order.getOrderDate()));
        Assert.assertTrue(newOrder.getBeerId().equals(order.getBeerId()));
        Assert.assertTrue(newOrder.getBeerQuantity() == order.getBeerQuantity());
        Assert.assertTrue((sizeBefore + 1) == orderDao.getAllOrders().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addOrderWithInvalidDate() {
        Order order = new Order("02.01.2010",
                1, 10, 3000);
        orderDao.addOrder(order);
    }

    @Test
    public void deleteOrderById() {
        Order order = new Order("2018-01-02",
                1, 10, 3000);

        Order newOrder = orderDao.addOrder(order);

        int sizeBefore = orderDao.getAllOrders().size();

        orderDao.deleteOrderById(newOrder.getOrderId());

        Assert.assertTrue((sizeBefore - 1) == orderDao.getAllOrders().size());

        exception.expect(EmptyResultDataAccessException.class);
        orderDao.getOrderById(newOrder.getOrderId());

    }

}
