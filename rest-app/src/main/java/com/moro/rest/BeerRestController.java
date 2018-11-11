package com.moro.rest;

import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.model.Review;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BeerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerRestController.class);

    private final BeerService beerService;

    private final ReviewService reviewService;

    @Autowired
    public BeerRestController(BeerService beerService, ReviewService reviewService) {
        this.beerService = beerService;
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/beerfactory/beers")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BeerDto> getBeerDtos() {
        LOGGER.debug("getBeerDtos()");

        return beerService.getAllBeers();
    }

    @GetMapping(value = "/beerfactory/beers/{beerId}")
    @ResponseStatus(HttpStatus.FOUND)
    public Beer getBeerById(@PathVariable(value = "beerId") int beerId) {
        LOGGER.debug("getBeerById({})", beerId);

        return beerService.getBeerById(beerId);
    }

    @PutMapping(value = "/beerfactory/beers/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBeer(@PathVariable(value = "beerId") int beerId,
                           @RequestBody Beer beer) {
        LOGGER.debug("editBeer({})", beer);

        beer.setBeerId(beerId);
        beerService.updateBeer(beer);
    }

    @PostMapping(value = "/beerfactory/beers")
    @ResponseStatus(HttpStatus.CREATED)
    public Beer addBeer(@RequestBody Beer beer) {
        LOGGER.debug("addBeer({})", beer);

        return beerService.addBeer(beer);
    }

    @DeleteMapping(value = "/beerfactory/beers/{beerId}")
    @ResponseStatus(HttpStatus.FOUND)
    public void deleteDepartmentById(@PathVariable(value = "beerId") int beerId) {
        LOGGER.debug("deleteBeerById({})", beerId);

        beerService.deleteBeerById(beerId);
    }

    @PostMapping(value = "/beerfactory/beers/review")
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@RequestBody Review review) {
        LOGGER.debug("addReview({})", review);

        return reviewService.addReview(review);
    }

}
