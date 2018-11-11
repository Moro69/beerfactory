package com.moro.soap.server.test;

import com.moro.model.Order;
import com.moro.service.interfaces.OrderService;
import com.moro.soap.implementation.OrderEndpointImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soap-spring-test.xml"})
public class OrderEndpointImplTest {

    @Autowired
    private OrderEndpointImpl endpoint;
    @Autowired
    private OrderService service;

    private Order order1;
    private Order order2;

    private static final String FROM_DATE = "2016-05-13";
    private static final String TO_DATE = "2018-12-12";

    @Before
    public void setUp() {
        order1 = new Order("2017-05-13", 1, 10, 1000);
        order1.setOrderId(1);

        order2 = new Order("2015-02-28", 2, 20, 2000);
        order2.setOrderId(2);
    }

    @After
    public void tearDown() {
        verify(service);
        reset(service);
    }

    @Test
    public void getAllOrders() {
        Collection<Order> orders = Arrays.asList(order1, order2);

        expect(service.getAllOrders())
                .andReturn(orders);
        replay(service);

        Collection<Order> allOrders = endpoint.getAllOrders();
        Assert.assertEquals(allOrders, orders);
    }

    @Test
    public void getOrdersByDate() {
        Collection<Order> order = Collections.singletonList(order1);

        expect(service.getOrdersByDate(FROM_DATE, TO_DATE))
                .andReturn(order);
        replay(service);

        Collection<Order> orderByDate = endpoint.getOrdersByDate(FROM_DATE, TO_DATE);
        Assert.assertEquals(orderByDate, order);
    }

    @Test
    public void getOrderById() {
        expect(service.getOrderById(order1.getOrderId()))
                .andReturn(order1);
        replay(service);

        Order order = endpoint.getOrderById(order1.getOrderId());
        Assert.assertEquals(order, order1);
    }

    @Test
    public void deleteOrderById() {
        service.deleteOrderById(order1.getOrderId());
        expectLastCall();
        replay(service);

        endpoint.deleteOrderById(order1.getOrderId());
    }

    @Test
    public void addOrder() {
        expect(service.addOrder(order1))
                .andReturn(order1);
        replay(service);

        Order order = endpoint.addOrder(order1);
        Assert.assertEquals(order, order1);
    }
}
