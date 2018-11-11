package com.moro.camel.integration.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.camel.integration.factory.EndpointFactory;
import com.moro.model.Review;
import com.moro.soap.reviewsoapservice.ReviewEndpoint;
import org.apache.camel.Exchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ReviewSoapManager {
    private ReviewEndpoint reviewEndpoint;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

    @Autowired
    public ReviewSoapManager(EndpointFactory factory,
                             ModelMapper modelMapper,
                             ObjectMapper objectMapper) {
        reviewEndpoint = factory.createReviewEndpoint();
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    public void addReview(Exchange exchange) throws IOException {

        exchange.getOut().setBody(
                modelMapper.map(reviewEndpoint.addReview(
                        objectMapper.readValue(exchange.getIn().getBody(String.class),
                                com.moro.soap.reviewsoapservice.Review.class)), Review.class)
        );
    }
}
