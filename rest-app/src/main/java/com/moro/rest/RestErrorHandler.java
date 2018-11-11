package com.moro.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String emptyResultDataAccessExceptionErrorHandler(EmptyResultDataAccessException e) {
        LOGGER.debug("emptyResultDataAccessExceptionErrorHandler({})");

        return "EmptyResultDataAccessException: Invalid id.";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String duplicateKeyExceptionHandler(DuplicateKeyException e) {
        LOGGER.debug("duplicateKeyExceptionHandler({})", e);

        return "DuplicateKeyException: beer with that name is already exist.";
    }
}
