package com.moro.rest.service.test;

import com.moro.model.Order;
import com.moro.rest.service.OrderRestServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-service-test.xml"})
public class OrderRestServiceImplTest {

    @Autowired
    private OrderRestServiceImpl orderRestService;
    @Autowired
    private RestTemplate restTemplateMock;

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
        verify(restTemplateMock);
        reset(restTemplateMock);
    }

    @Test
    public void getAllOrders() {
        Collection<Order> orders1 = Arrays.asList(order1, order2);
        ResponseEntity entity = new ResponseEntity(orders1, HttpStatus.FOUND);

        expect(restTemplateMock.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(restTemplateMock);

        Collection<Order> orders = orderRestService.getAllOrders();
        Assert.assertNotNull(orders);
        Assert.assertEquals(orders, orders1);
    }

    @Test
    public void getOrdersByDate() {
        Collection<Order> order = Collections.singletonList(order1);
        ResponseEntity entity = new ResponseEntity(order, HttpStatus.FOUND);

        expect(restTemplateMock.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(restTemplateMock);

        Collection<Order> orders = orderRestService.getOrdersByDate(FROM_DATE, TO_DATE);
        Assert.assertNotNull(orders);
        Assert.assertEquals(orders, order);
    }

    @Test
    public void getOrderById() {
        expect(restTemplateMock.getForObject(anyString(), anyObject(), anyObject(MultiValueMap.class)))
                .andReturn(order1);
        replay(restTemplateMock);

        Order order = orderRestService.getOrderById(order1.getOrderId());
        Assert.assertEquals(order, order1);
    }

    @Test
    public void addOrder() {
        expect(restTemplateMock.postForObject(anyString(), anyObject(), anyObject()))
                .andReturn(order1);
        replay(restTemplateMock);

        Order order = orderRestService.addOrder(order1);
        Assert.assertEquals(order, order1);
    }

    @Test
    public void deleteOrderById() {
        restTemplateMock.delete(anyString());
        expectLastCall();
        replay(restTemplateMock);

        orderRestService.deleteOrderById(order1.getOrderId());
    }

}
