package com.moro.rest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moro.model.Order;
import com.moro.rest.OrderRestController;
import com.moro.service.interfaces.OrderService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-mock-test.xml"})
public class OrderRestControllerMockTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRestController orderRestController;

    private MockMvc mockMvc;

    private Order order1;
    private Order order2;

    @Before
    public void setUp() {
        order1 = new Order("2017-05-13", 1, 10, 1000);
        order1.setOrderId(1);

        order2 = new Order("2015-02-28", 2, 20, 2000);
        order2.setOrderId(2);

        mockMvc = MockMvcBuilders.standaloneSetup(orderRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(orderService);
        reset(orderService);
    }

    @Test
    public void getAllOrders() throws Exception {
        expect(orderService.getAllOrders())
                .andReturn(Arrays.asList(order1, order2));
        replay(orderService);

        mockMvc.perform(
                get("/beerfactory/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].orderId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].orderDate", Matchers.is("2017-05-13")))
                .andExpect(jsonPath("$[0].beerQuantity", Matchers.is(10)))
                .andExpect(jsonPath("$[0].orderPrice", Matchers.is(1000)))
                .andExpect(jsonPath("$[1].orderId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].orderDate", Matchers.is("2015-02-28")))
                .andExpect(jsonPath("$[1].beerQuantity", Matchers.is(20)))
                .andExpect(jsonPath("$[1].orderPrice", Matchers.is(2000)));
    }

    @Test
    public void getOrdersByDate() throws Exception {
        expect(orderService.getOrdersByDate(order1.getOrderDate(), order2.getOrderDate()))
                .andReturn(Arrays.asList(order1, order2));
        replay(orderService);

        mockMvc.perform(
                get("/beerfactory/orders?fromDate={fromDate}&toDate={toDate}",
                        order1.getOrderDate(), order2.getOrderDate())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].orderId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].orderDate", Matchers.is("2017-05-13")))
                .andExpect(jsonPath("$[0].beerQuantity", Matchers.is(10)))
                .andExpect(jsonPath("$[0].orderPrice", Matchers.is(1000)))
                .andExpect(jsonPath("$[1].orderId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].orderDate", Matchers.is("2015-02-28")))
                .andExpect(jsonPath("$[1].beerQuantity", Matchers.is(20)))
                .andExpect(jsonPath("$[1].orderPrice", Matchers.is(2000)));
    }

    @Test
    public void getOrderById() throws Exception {
        expect(orderService.getOrderById(anyInt()))
                .andReturn(order1);
        replay(orderService);

        mockMvc.perform(get("/beerfactory/orders/{orderId}", order1.getOrderId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(jsonPath("$.orderDate", Matchers.is("2017-05-13")))
                .andExpect(jsonPath("$.beerQuantity", Matchers.is(10)))
                .andExpect(jsonPath("$.orderPrice", Matchers.is(1000)));
    }


    @Test
    public void addOrder() throws Exception {
        expect(orderService.addOrder(anyObject()))
                .andReturn(order1);
        replay(orderService);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/beerfactory/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order1))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.orderId", Matchers.is(1)))
                .andExpect(jsonPath("$.orderDate", Matchers.is("2017-05-13")))
                .andExpect(jsonPath("$.beerQuantity", Matchers.is(10)))
                .andExpect(jsonPath("$.orderPrice", Matchers.is(1000)));
    }

    @Test
    public void deleteOrderById() throws Exception {
        orderService.deleteOrderById(order1.getOrderId());
        expectLastCall();
        replay(orderService);

        mockMvc.perform(
                delete("/beerfactory/orders/{orderId}", order1.getOrderId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }
}
