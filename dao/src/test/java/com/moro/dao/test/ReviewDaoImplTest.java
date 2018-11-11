package com.moro.dao.test;


import com.moro.dao.interfaces.ReviewDao;
import com.moro.model.Review;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-db-spring.xml",
        "classpath:test-dao.xml", "classpath:dao.xml"})
@Rollback
@Transactional
public class ReviewDaoImplTest {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    public void addReview() {

        Review review = new Review(1, "Very nice.", 5);

        review = reviewDao.addReview(review);

        Assert.assertNotNull(review);
        Assert.assertTrue(review.getReviewId() == 6);
    }
}
