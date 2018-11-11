package com.moro.soap.implementation;

import com.moro.model.Review;
import com.moro.service.interfaces.ReviewService;
import com.moro.soap.interfaces.ReviewEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

@WebService(endpointInterface = "com.moro.soap.interfaces.ReviewEndpoint",
        serviceName = "reviewProcess", portName = "ReviewProcessPort",
        targetNamespace = "http://moro.com/soap/reviewsoapservice")
public class ReviewEndpointImpl implements ReviewEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewEndpointImpl.class);

    private ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public Review addReview(Review review) {
        LOGGER.debug("addReview({})", review);

        return reviewService.addReview(review);
    }
}
