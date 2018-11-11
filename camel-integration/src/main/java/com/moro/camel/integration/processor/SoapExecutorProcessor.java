package com.moro.camel.integration.processor;

import com.moro.camel.integration.soap.BeerSoapManager;
import com.moro.camel.integration.soap.OrderSoapManager;
import com.moro.camel.integration.soap.ReviewSoapManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;


public class SoapExecutorProcessor implements Processor {

    private final OrderSoapManager orderManager;
    private final BeerSoapManager beerManager;
    private final ReviewSoapManager reviewManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapExecutorProcessor.class);

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

    private final UriTemplate beerByIdUriTemplate;
    private final UriTemplate orderByIdUriTemplate;

    @Autowired
    public SoapExecutorProcessor(final OrderSoapManager orderManager, final BeerSoapManager beerManager,
                                 final ReviewSoapManager reviewManager, final UriTemplate beerByIdUriTemplate,
                                 final UriTemplate orderByIdUriTemplate) {
        this.orderManager = orderManager;
        this.beerManager = beerManager;
        this.reviewManager = reviewManager;
        this.beerByIdUriTemplate = beerByIdUriTemplate;
        this.orderByIdUriTemplate = orderByIdUriTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
        URI uri = new URI(request.getRequestURI());
        String uriPath = uri.getPath();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        LOGGER.info("URI IS " + uri);
        LOGGER.info("URL IS " + request.getRequestURL());
        LOGGER.info("QUERY IS " + request.getQueryString());
        LOGGER.info("METHOD IS " + method);

        switch (method) {
            case GET:
                getMethodParser(exchange, uriPath, request);
                break;

            case POST:
                postMethodParser(exchange, uriPath);
                break;

            case PUT:
                putMethodParser(exchange, uriPath);
                break;

            case DELETE:
                deleteMethodParser(exchange, uriPath);
                break;

            default:
                throw new IllegalArgumentException("Unavailable method.");
        }
    }

    private void getMethodParser(Exchange exchange, String uri, HttpServletRequest request) {
        if(uri.contains(beersUri)) {
            LOGGER.info("ENDPOINT IS BEERS");
            if (beersUri.equals(uri)) {
                LOGGER.info("OPERATION is getAllBeers");
                beerManager.getAllBeers(exchange);
            } else if (beerByIdUriTemplate.matches(uri)) {
                LOGGER.info("OPERATION is getBeerById");
                int beerId = Integer.parseInt(beerByIdUriTemplate.match(uri).get("beerId"));
                beerManager.getBeerById(exchange, beerId);
            }
        } else if(uri.contains(ordersUri)) {
            LOGGER.info("ENDPOINT IS ORDERS");
            if (request.getQueryString() != null) {
                LOGGER.info("OPERATION is getOrdersByDate");
                orderManager.getOrdersByDate(exchange, request);

            } else if (ordersUri.equals(uri)) {
                LOGGER.info("OPERATION is getAllOrders");
                orderManager.getAllOrders(exchange);

            } else if(orderByIdUriTemplate.matches(uri)) {
                LOGGER.info("OPERATION is getOrderById");
                int orderId = Integer.parseInt(orderByIdUriTemplate.match(uri).get("orderId"));
                orderManager.getOrderById(exchange, orderId);
            }
        } else throw new IllegalArgumentException("Invalid URI");
    }

    private void postMethodParser(Exchange exchange, String uri) throws IOException {
        if(beersUri.equals(uri)) {
            LOGGER.info("ENDPOINT IS BEERS");
            LOGGER.info("OPERATION is addBeer");
            beerManager.addBeer(exchange);
        } else if (ordersUri.equals(uri)) {
            LOGGER.info("ENDPOINT IS ORDERS");
            LOGGER.info("OPERATION is addOrder");
            orderManager.addOrder(exchange);
        } else if(reviewUri.equals(uri)) {
            LOGGER.info("ENDPOINT IS REVIEW");
            LOGGER.info("OPERATION is addReview");
            reviewManager.addReview(exchange);
        } else throw new IllegalArgumentException("Invalid URI");
    }

    private void deleteMethodParser(Exchange exchange, String uri) {
        if(beerByIdUriTemplate.matches(uri)) {
            LOGGER.info("ENDPOINT IS BEERS");
            LOGGER.info("OPERATION is deleteBeerById");
            int beerId = Integer.parseInt(beerByIdUriTemplate.match(uri).get("beerId"));
            beerManager.deleteBeerById(exchange, beerId);
        } else if(orderByIdUriTemplate.matches(uri)) {
            LOGGER.info("ENDPOINT IS ORDERS");
            LOGGER.info("OPERATION is deleteOrderById");
            int orderId = Integer.parseInt(orderByIdUriTemplate.match(uri).get("orderId"));
            orderManager.deleteOrderById(exchange, orderId);
        } else throw new IllegalArgumentException("Invalid URI");
    }

    private void putMethodParser(Exchange exchange, String uri) throws IOException {
        if(beerByIdUriTemplate.matches(uri)) {
            LOGGER.info("ENDPOINT IS BEERS");
            LOGGER.info("OPERATION is updateBeer");
            beerManager.updateBeer(exchange);
        } else throw new IllegalArgumentException("Invalid URI");
    }
}
