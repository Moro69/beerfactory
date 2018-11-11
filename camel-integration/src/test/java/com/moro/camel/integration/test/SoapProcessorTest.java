package com.moro.camel.integration.test;

import com.moro.camel.integration.processor.SoapExecutorProcessor;
import com.moro.camel.integration.soap.BeerSoapManager;
import com.moro.camel.integration.soap.OrderSoapManager;
import com.moro.camel.integration.soap.ReviewSoapManager;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:camel-spring-test.xml"})
public class SoapProcessorTest {

    private SoapExecutorProcessor processor;

    @Mock
    private OrderSoapManager orderManager;
    @Mock
    private BeerSoapManager beerManager;
    @Mock
    private ReviewSoapManager reviewManager;

    @Autowired
    private UriTemplate beerByIdUriTemplate;
    @Autowired
    private UriTemplate orderByIdUriTemplate;

    private MockHttpServletRequest httpRequest;
    private Exchange exchange;

    @Value("${beers.template}")
    private String beersUri;
    @Value("${beerById.template}")
    private String beerByIdUri;
    @Value("${orders.template}")
    private String ordersUri;
    @Value("${orderById.template}")
    private String orderByIdUri;
    @Value("${review.template}")
    private String reviewUri;

    private static final int BEER_ID = 1;
    private static final int ORDER_ID = 1;
    private static final String ORDERS_BY_DATE_QUERY = "fromDate=2020-02-02&toDate=2020-03-03";

    @Before
    public void setUp() {
        initMocks(this);
        processor = new SoapExecutorProcessor(orderManager, beerManager,
                                                reviewManager, beerByIdUriTemplate,
                                                    orderByIdUriTemplate);

        ReflectionTestUtils.setField(processor, "beersUri", beersUri);
        ReflectionTestUtils.setField(processor, "beerByIdUri", beerByIdUri);
        ReflectionTestUtils.setField(processor, "ordersUri", ordersUri);
        ReflectionTestUtils.setField(processor, "orderByIdUri", orderByIdUri);
        ReflectionTestUtils.setField(processor, "reviewUri", reviewUri);

        exchange = new DefaultExchange(new DefaultCamelContext());
    }

    @Test
    public void getAllBeers() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.GET.toString(), "/beerfactory/beers");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(beerManager).getAllBeers(exchange);
    }

    @Test
    public void getBeerById() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.GET.toString(), "/beerfactory/beers/1");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(beerManager).getBeerById(exchange, BEER_ID);
    }

    @Test
    public void addBeer() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.POST.toString(), "/beerfactory/beers");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(beerManager).addBeer(exchange);
    }

    @Test
    public void deleteBeerById() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.DELETE.toString(), "/beerfactory/beers/1");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(beerManager).deleteBeerById(exchange, BEER_ID);
    }

    @Test
    public void updateBeer() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.PUT.toString(), "/beerfactory/beers/1");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(beerManager).updateBeer(exchange);
    }

    @Test
    public void addReview() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.POST.toString(), "/beerfactory/beers/review");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(reviewManager).addReview(exchange);
    }

    @Test
    public void getAllOrders() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.GET.toString(), "/beerfactory/orders");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(orderManager).getAllOrders(exchange);
    }

    @Test
    public void getOrdersByDate() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.GET.toString(), "/beerfactory/orders");
        httpRequest.setQueryString(ORDERS_BY_DATE_QUERY);
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(orderManager).getOrdersByDate(exchange, httpRequest);
    }

    @Test
    public void addOrder() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.POST.toString(), "/beerfactory/orders");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(orderManager).addOrder(exchange);
    }

    @Test
    public void deleteOrderById() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.DELETE.toString(), "/beerfactory/orders/1");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(orderManager).deleteOrderById(exchange, ORDER_ID);
    }

    @Test
    public void getOrderById() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.GET.toString(), "/beerfactory/orders/1");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);
        verify(orderManager).getOrderById(exchange, ORDER_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidGetUriException() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.GET.toString(), "/invalid/uri");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPostUriException() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.POST.toString(), "/invalid/uri");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidDeleteUriException() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.DELETE.toString(), "/invalid/uri");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPutUriException() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.PUT.toString(), "/invalid/uri");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);

    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMethodException() throws Exception {
        httpRequest = new MockHttpServletRequest(HttpMethod.PATCH.toString(), "/invalid/uri");
        exchange.getIn().setBody(httpRequest, HttpServletRequest.class);

        processor.process(exchange);

    }
}
