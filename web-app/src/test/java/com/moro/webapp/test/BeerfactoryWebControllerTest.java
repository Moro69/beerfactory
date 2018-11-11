package com.moro.webapp.test;

import com.moro.model.BeerDto;
import com.moro.model.Order;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.OrderService;
import com.moro.web_app.controllers.BeerfactoryWebController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:webapp-test-context.xml"})
@Rollback
public class BeerfactoryWebControllerTest {

    @Autowired
    private BeerService beerServiceMock;
    @Autowired
    private OrderService orderServiceMock;
    @Autowired
    private BeerfactoryWebController beerfactoryWebController;

    private MockMvc mockMvc;

    private BeerDto beerDto1;
    private BeerDto beerDto2;

    private Order order1;
    private Order order2;

    private static final String FROM_DATE = "2016-05-13";
    private static final String TO_DATE = "2018-12-12";

    @Before
    public void setUp()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("/WEB-INF/templates/");

        mockMvc = MockMvcBuilders.standaloneSetup(beerfactoryWebController)
                .setViewResolvers(viewResolver)
                .build();

        order1 = new Order("2017-05-13", 1, 10, 1000);
        order1.setOrderId(1);

        order2 = new Order("2015-02-28", 2, 20, 2000);
        order2.setOrderId(2);

        beerDto1 = new BeerDto(1, "beerDto1",
                4.5, "beer dto 1", 10, 3.3 , "beerDto1");

        beerDto2 = new BeerDto(2, "beerDto2",
                4.7, "beer dto 2", 20, 5.0 , "beerDto2");

    }

    @Test
    public void getAllBeers() throws Exception {
        Collection<BeerDto> beers = Arrays.asList(beerDto1, beerDto2);
        expect(beerServiceMock.getAllBeers())
                .andReturn(beers);
        replay(beerServiceMock);

        mockMvc.perform(get("/beerfactory/beers"))
                .andExpect(view().name("beers"))
                .andExpect(model().attribute("beers", beers));

        verify(beerServiceMock);
        reset(beerServiceMock);
    }

    @Test
    public void getOrders() throws Exception {
        Collection<Order> orders = Arrays.asList(order1, order2);
        expect(orderServiceMock.getAllOrders())
                .andReturn(orders);
        replay(orderServiceMock);

        mockMvc.perform(get("/beerfactory/orders"))
                .andExpect(view().name("orders"))
                .andExpect(model().attribute("orders", orders));

        verify(orderServiceMock);
        reset(orderServiceMock);
    }

    @Test
    public void getOrdersByDate() throws Exception {
        Collection<Order> orders = Collections.singletonList(order1);
        expect(orderServiceMock.getOrdersByDate(FROM_DATE, TO_DATE))
                .andReturn(orders);
        replay(orderServiceMock);

        mockMvc.perform(get("/beerfactory/orders")
        .param("fromDate", FROM_DATE)
        .param("toDate", TO_DATE))
                .andExpect(view().name("orders"))
                .andExpect(model().attribute("orders", orders));

        verify(orderServiceMock);
        reset(orderServiceMock);
    }
}
