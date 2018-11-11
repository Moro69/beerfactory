package com.moro.webapp.test;

import com.moro.model.BeerDto;
import com.moro.model.Order;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.OrderService;
import com.moro.web_app.controllers.OrderWebController;
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

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:webapp-test-context.xml"})
@Rollback
public class OrderWebControllerTest {

    @Autowired
    private OrderService orderServiceMock;
    @Autowired
    private BeerService beerServiceMock;
    @Autowired
    private OrderWebController orderController;

    private MockMvc mockMvc;

    private BeerDto beerDto1;
    private BeerDto beerDto2;

    private Order order;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("/WEB-INF/templates/");

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setViewResolvers(viewResolver)
                .build();

        beerDto1 = new BeerDto(1, "beerDto1",
                4.5, "beer dto 1", 10, 3.3 , "beerDto1");

        beerDto2 = new BeerDto(2, "beerDto2",
                4.7, "beer dto 2", 20, 5.0 , "beerDto2");

        order = new Order("2017-05-13", 1, 10, 1000);
        order.setOrderId(1);
    }


    @Test
    public void getAddOrder() throws Exception {
        Collection<BeerDto> beers = Arrays.asList(beerDto1, beerDto2);
        expect(beerServiceMock.getAllBeers())
                .andReturn(beers);
        replay(beerServiceMock);

        mockMvc.perform(get("/beerfactory/orders/neworder"))
                .andDo(print())
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("order", new Order()))
                .andExpect(model().attributeExists("beers"))
                .andExpect(model().attribute("beers", beers));

        verify(beerServiceMock);
        reset(beerServiceMock);
    }

    @Test
    public void postAddOrder() throws Exception {
        expect(orderServiceMock.addOrder(anyObject()))
                .andReturn(order);
        replay(orderServiceMock);

        mockMvc.perform(post("/beerfactory/orders/neworder")
        .param("orderDate", order.getOrderDate())
        .param("beerId", String.valueOf(order.getBeerId()))
        .param("beerQuantity", String.valueOf(order.getBeerQuantity()))
        .param("orderPrice", String.valueOf(order.getOrderPrice())))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/beerfactory/orders"));

        verify(orderServiceMock);
        reset(orderServiceMock);

    }

    @Test
    public void deleteOrder() throws Exception {
        orderServiceMock.deleteOrderById(anyInt());
        expectLastCall();
        replay(orderServiceMock);

        mockMvc.perform(get("/beerfactory/orders/delete/{orderId}", order.getOrderId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/beerfactory/orders"));
        verify(orderServiceMock);
        reset(orderServiceMock);
    }
}
