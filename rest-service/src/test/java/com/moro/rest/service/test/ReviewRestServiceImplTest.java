package com.moro.rest.service.test;

import com.moro.model.Review;
import com.moro.rest.service.ReviewRestServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-service-test.xml"})
public class ReviewRestServiceImplTest {

    @Autowired
    private ReviewRestServiceImpl reviewRestService;
    @Autowired
    private RestTemplate restTemplateMock;

    private Review review;

    @Before
    public void setUp() {
        review = new Review(1, "good", 4.5);
        review.setReviewId(1);
    }

    @Test
    public void addReview() {
        expect(restTemplateMock.postForObject(anyString(), anyObject(), anyObject()))
                .andReturn(review);
        replay(restTemplateMock);

        Review addedReview = reviewRestService.addReview(review);
        Assert.assertEquals(review, addedReview);

    }

}

