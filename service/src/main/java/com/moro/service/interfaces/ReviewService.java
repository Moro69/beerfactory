package com.moro.service.interfaces;

import com.moro.model.Review;
import org.springframework.stereotype.Service;

/**
 * Review service interface.
 */
@Service
public interface ReviewService {

    /**
     * Add new review.
     * @param review beer review.
     * @return added rating with id.
     */
    Review addReview(Review review);
}
