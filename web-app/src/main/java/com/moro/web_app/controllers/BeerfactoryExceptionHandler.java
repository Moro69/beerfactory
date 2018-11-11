package com.moro.web_app.controllers;

import com.moro.model.Beer;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class BeerfactoryExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc,
                                               Model model) {
        model.addAttribute("errorMessage", "File size must be not larger than 2MB");
        model.addAttribute("beer", new Beer());

        return "beer";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException exc,
                                         Model model) {
        model.addAttribute("errorMessage", "Invalid image format. PNG and JPEG are only available.");
        model.addAttribute("beer", new Beer());
        return "beer";
    }
}
