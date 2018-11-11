package com.moro.camel.integration.factory;

import com.moro.soap.beersoapservice.BeerEndpoint;
import com.moro.soap.beersoapservice.BeerProcess;
import com.moro.soap.ordersoapservice.OrderEndpoint;
import com.moro.soap.ordersoapservice.OrderProcess;
import com.moro.soap.reviewsoapservice.ReviewEndpoint;
import com.moro.soap.reviewsoapservice.ReviewProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.net.MalformedURLException;
import java.net.URL;


@PropertySource("classpath:wsdls.properties")
public class EndpointFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointFactory.class);

    @Value("${beers.wsdl}")
    private String beersWsdl;
    @Value("${orders.wsdl}")
    private String ordersWsdl;
    @Value("${review.wsdl}")
    private String reviewWsdl;

    private final BeerProcess beerProcess;
    private final OrderProcess orderProcess;
    private final ReviewProcess reviewProcess;

    @Autowired
    public EndpointFactory(final BeerProcess beerProcess,
                           final OrderProcess orderProcess,
                           final ReviewProcess reviewProcess) {
        this.beerProcess = beerProcess;
        this.orderProcess = orderProcess;
        this.reviewProcess = reviewProcess;
    }

    public BeerEndpoint createBeerEndpoint() {
        LOGGER.info("create beer endpoint");
        return beerProcess.getBeerProcessPort();
    }

    public OrderEndpoint createOrderEndpoint() {
        LOGGER.info("create order endpoint");
        return orderProcess.getOrderProcessPort();
    }

    public ReviewEndpoint createReviewEndpoint() {
        LOGGER.info("create review endpoint");
        return reviewProcess.getReviewProcessPort();
    }

    private URL createUrl(String wsdlUrl) throws MalformedURLException {
                return new URL(wsdlUrl);
    }
}
