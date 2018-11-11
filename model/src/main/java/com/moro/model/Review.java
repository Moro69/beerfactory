package com.moro.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Review POJO.
 */
public class Review {

    /**
     * Review id.
     */
    private Integer reviewId;

    /**
     * beer id of review.
     */
    @NotNull
    private Integer beerId;

    /**
     * Review comment.
     */
    @Size(max = 512, message = "Length must be between 2 and 512.")
    private String comment;

    /**
     * beer rating in review.
     */
    //@Positive(message = "Rate this beer.")
    private double rating;

    /**
     * Constructor with arguments.
     *
     * @param beerId beer id.
     * @param comment review comment.
     * @param rating beer rating.
     */
    public Review(final Integer beerId, final String comment,
                  final double rating) {
        this.beerId = beerId;
        this.comment = comment;
        this.rating = rating;
    }

    /**
     * Constructor by default.
     */
    public Review() {

    }

    /**
     * Get review id.
     *
     * @return review id.
     */
    public final Integer getReviewId() {
        return reviewId;
    }

    /**
     * Set review id.
     *
     * @param reviewId review id.
     */
    public final void setReviewId(final Integer reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Get beer id.
     * @return beer id.
     */
    public final Integer getBeerId() {
        return beerId;
    }

    /**
     * Set beer id.
     * @param beerId beer id.
     */
    public final void setBeerId(final Integer beerId) {
        this.beerId = beerId;
    }

    /**
     * Get review comment.
     *
     * @return review comment.
     */
    public final String getComment() {
        return comment;
    }

    /**
     * Set review comment.
     * @param comment review comment.
     */
    public final void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * Get beer rating.
     *
     * @return beer rating.
     */
    public final double getRating() {
        return rating;
    }

    /**
     * Set beer rating.
     *
     * @param rating beer rating.
     */
    public final void setRating(final double rating) {
        this.rating = rating;
    }

    /**
     * Overridden toString method.
     *
     * @return string view of object.
     */
    @Override
    public final String toString() {
        return "Review{"
                + "reviewId=" + reviewId
                + ", beerId=" + beerId
                + ", comment='" + comment
                + '\''
                + ", rating=" + rating
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Double.compare(review.rating, rating) == 0 &&
                Objects.equals(reviewId, review.reviewId) &&
                Objects.equals(beerId, review.beerId) &&
                Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewId, beerId, comment, rating);
    }
}
