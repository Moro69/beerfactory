package com.moro.web_app.controllers;

import com.moro.model.Beer;
import com.moro.model.Review;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class BeerWebController {

    /**
     * Logger for beer controller.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeerWebController.class)   ;

    private final BeerService beerService;
    private final ReviewService reviewService;

    private static final String IMAGE_PATH = "./src/main/webapp/resources/static/images/";

    @Autowired
    public BeerWebController(BeerService beerService,
                             ReviewService reviewService) {
        this.beerService = beerService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/beerfactory/beers/edit/{beerId}")
    public final String editBeer(@PathVariable int beerId,
                                 Model model) {
        LOGGER.debug("editBeer({})", beerId);

        Beer beer = beerService.getBeerById(beerId);

        model.addAttribute("beer", beer);
        model.addAttribute("isNew", false);

        return "beer";
    }

    @PostMapping(value = "/beerfactory/beers/edit/{beerId}")
    public final String editBeer(@Valid Beer beer,
                                 BindingResult result,
                                 @PathVariable int beerId,
                                 @RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {
        LOGGER.debug("editBeer({}), {}", beer, result);

        if (result.hasErrors())
            return "beer";

        if(image == null) {
            beer.setBeerImage("default");
        } else if (!image.isEmpty()) {
            validateImage(image);
            LOGGER.info("Image is valid.");

            String fileName =
                    beer.getBeerTitle().replace(" ","-").toLowerCase();

            saveImage(fileName, image);
            beer.setBeerImage(fileName);
        } else beer.setBeerImage("default");

        beerService.updateBeer(beer);

        return "redirect:/beerfactory/beers";
    }

    @GetMapping(value = "/beerfactory/beers/newbeer")
    public final String addBeer(Model model) {
        LOGGER.debug("addBeer()");

        Beer beer = new Beer();

        model.addAttribute("beer", beer);
        model.addAttribute("isNew", true);

        return "beer";
    }

    @PostMapping(value = "/beerfactory/beers/newbeer")
    public final String addBeer(@Valid Beer beer,
                                BindingResult result,
                                @RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {
        LOGGER.debug("addBeer({}), {}", beer, result);
        LOGGER.info("IMAGE SIZE " + image.getSize());
        if (result.hasErrors())
            return "beer";

        String fileName = "default";

        if (!image.isEmpty()) {
            validateImage(image);

            fileName =
                    beer.getBeerTitle().replace(" ", "-").toLowerCase();

            saveImage(fileName, image);
            beer.setBeerImage(fileName);
        } else {
            beer.setBeerImage(fileName);
        }

        beerService.addBeer(beer);

        return "redirect:/beerfactory/beers";
    }

    @GetMapping(value = "/beerfactory/beers/delete/{beerId}")
    public final String deleteBeerById(@PathVariable int beerId) {

        LOGGER.debug("deleteBeerById({})", beerId);

        beerService.deleteBeerById(beerId);

        return "redirect:/beerfactory/beers";
    }

    @PostMapping(value = "/beerfactory/beers/review")
    public final String addReview(@Valid Review review, BindingResult result) {
        LOGGER.debug("addReview({}) result: {}", review, result);

        if (result.hasErrors()) {
            return "beers";
        } else {
            reviewService.addReview(review);

            return "redirect:/beerfactory/beers";
        }

    }

    private void validateImage(MultipartFile image) throws IOException {
        if (!image.getContentType().equals("image/jpeg")
                && !image.getContentType().equals("image/png")) {
            throw new IllegalArgumentException("Only JPG and PNG images are accepted.");
        } else if (image.getSize() > 2000000) {
            throw new MaxUploadSizeExceededException(image.getSize());
        }
    }

    private void saveImage(String fileName, MultipartFile image) throws IOException {

        File file = new File(IMAGE_PATH + fileName + ".jpg");

        BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(file));
            stream.write(image.getBytes());

    }
}
