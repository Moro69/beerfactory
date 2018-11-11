package com.moro.soap.server.test;

import com.moro.model.Review;
import com.moro.service.interfaces.ReviewService;
import com.moro.soap.implementation.ReviewEndpointImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soap-spring-test.xml"})
public class ReviewEndpointImplTest {

    @Autowired
    private ReviewService service;
    @Autowired
    private ReviewEndpointImpl reviewEndpoint;

    private Review review;

    @Before
    public void setUp() {
        review = new Review(1, "good", 4.5);
        review.setReviewId(1);
    }

    @Test
    public void addReview() {
        expect(service.addReview(review))
                .andReturn(review);
        replay(service);

        Review newReview = reviewEndpoint.addReview(review);
        Assert.assertEquals(newReview, review);
    }
}
