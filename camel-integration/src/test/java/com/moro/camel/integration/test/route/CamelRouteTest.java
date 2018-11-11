package com.moro.camel.integration.test.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.camel.integration.mbeans.Direction;
import com.moro.camel.integration.processor.SoapExecutorProcessor;
import com.moro.camel.integration.rest.CamelRestRoute;
import com.moro.model.Beer;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.*;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:camel-spring-test.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CamelRouteTest extends CamelTestSupport {

    private static final String DIRECTION_PROPERTY = "direction";
    private static final String DIRECT_START = "direct:start";

    @Autowired
    private Direction routeDirection;
    @Autowired
    private ObjectMapper mapper;
    @Mock
    private SoapExecutorProcessor processor;

    private MockEndpoint restMock;

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

    @Value("${camel.endpoint}")
    private String camelEndpoint;
    @Value("${rest.endpoint}")
    private String restEndpoint;

    @Override
    protected void doPostSetup() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith(DIRECT_START);
                weaveById("rest").replace().to("mock:rest");
                weaveById("soap").replace().to("mock:soap");
            }
        });
    }

    @Before
    public void initMock() {
        restMock = getMockEndpoint("mock:rest");
    }

    @Override
    public boolean isCreateCamelContextPerClass() {
        return true;
    }

    @Override
    protected void doPreSetup() {
        initMocks(this);
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        CamelRestRoute route = new CamelRestRoute(processor, routeDirection);
        ReflectionTestUtils.setField(route, "camelEndpoint", camelEndpoint);
        ReflectionTestUtils.setField(route, "restEndpoint", restEndpoint);

        return route;
    }

    @Test
    public void getForRest() throws Exception {
        restMock.expectedBodiesReceived("");
        restMock.expectedMessageCount(1);
        restMock.expectedHeaderReceived(Exchange.HTTP_METHOD, HttpMethod.GET);
        restMock.expectedHeaderReceived(Exchange.HTTP_URI, beersUri);
        restMock.expectedPropertyReceived(DIRECTION_PROPERTY, "rest");

        Map<String, Object> headers = new HashMap<>();
        headers.put(Exchange.HTTP_METHOD, HttpMethod.GET);
        headers.put(Exchange.HTTP_URI, beersUri);

        template.requestBodyAndHeaders(DIRECT_START, "", headers);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void postForRest() throws InterruptedException, IOException {
        Beer beer = new Beer("test", 5.0, "beer", 15, "image");
        beer.setBeerId(1);

        restMock.expectedHeaderReceived(Exchange.HTTP_METHOD, HttpMethod.POST);
        restMock.expectedHeaderReceived(Exchange.HTTP_URI, beersUri);
        restMock.expectedPropertyReceived(DIRECTION_PROPERTY, "rest");

        Map<String, Object> headers = new HashMap<>();
        headers.put(Exchange.HTTP_METHOD, HttpMethod.POST);
        headers.put(Exchange.HTTP_URI, beersUri);

        template.requestBodyAndHeaders(DIRECT_START,
                mapper.writeValueAsString(beer), headers);

        Beer newBeer = mapper.readValue(restMock.getExchanges().get(0).getIn().getBody(String.class), Beer.class);

        assertMockEndpointsSatisfied();
        assertEquals(newBeer, beer);
    }

    @Test
    public void getWithQueryRest() throws InterruptedException {
        restMock.expectedBodiesReceived("");
        restMock.expectedHeaderReceived(Exchange.HTTP_METHOD, HttpMethod.GET);
        restMock.expectedHeaderReceived(Exchange.HTTP_URI, ordersUri);
        restMock.expectedHeaderReceived(Exchange.HTTP_QUERY, "fromDate=2020-02-02&toDate=2020-03-03");
        restMock.expectedPropertyReceived(DIRECTION_PROPERTY, "rest");

        Map<String, Object> headers = new HashMap<>();
        headers.put(Exchange.HTTP_METHOD, HttpMethod.GET);
        headers.put(Exchange.HTTP_URI, ordersUri);
        headers.put(Exchange.HTTP_QUERY, "fromDate=2020-02-02&toDate=2020-03-03");

        template.requestBodyAndHeaders(DIRECT_START,
                "", headers);

        assertMockEndpointsSatisfied();
    }
}
