package com.moro.soap.interfaces;

import com.moro.model.Review;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://moro.com/soap/reviewsoapservice")
@SOAPBinding
public interface ReviewEndpoint {

    /**
     * Add new review.
     *
     * @param review beer review.
     * @return added rating with id.
     */
    @WebMethod
    Review addReview(Review review);
}
