package com.moro.dao.interfaces;


import com.moro.model.Review;

/**
 * Review DAO interface.
 */
public interface ReviewDao {

    /**
     * Add new review.
     * @param review beer review.
     * @return added rating with id.
     */
    Review addReview(Review review);
}
