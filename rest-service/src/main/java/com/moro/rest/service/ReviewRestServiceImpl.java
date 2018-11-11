package com.moro.rest.service;

import com.moro.model.Review;
import com.moro.service.interfaces.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class ReviewRestServiceImpl implements ReviewService {

    @Value("${review.url}")
    private String reviewUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRestServiceImpl.class);
    private final RestTemplate restTemplate;

    @Autowired
    public ReviewRestServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Review addReview(Review review) {
        LOGGER.debug("addReview({})", review);
        Review newReview =
                restTemplate.postForObject(reviewUrl, review, Review.class);

        return newReview;
    }
}
