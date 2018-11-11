package com.moro.dbService;

import com.moro.dao.interfaces.ReviewDao;
import com.moro.model.Review;
import com.moro.service.interfaces.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ReviewDbServiceImpl implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDbServiceImpl.class);

    private ReviewDao reviewDao;

    @Autowired
    public ReviewDbServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Review addReview(Review review) {
        LOGGER.debug("addReview({})", review);

        return reviewDao.addReview(review);
    }
}
