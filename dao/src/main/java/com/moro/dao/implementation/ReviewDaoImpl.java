package com.moro.dao.implementation;

import com.moro.dao.interfaces.ReviewDao;
import com.moro.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ReviewDaoImpl implements ReviewDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    @Value("${review.insert}")
    private String insertSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Review addReview(Review review) {
        LOGGER.debug("addReview({})", review);

        SqlParameterSource namedParameters =
                new BeanPropertySqlParameterSource(review);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                insertSql, namedParameters, keyHolder);

        review.setReviewId(keyHolder.getKey().intValue());

        return review;
    }
}
