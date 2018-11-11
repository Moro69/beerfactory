package com.moro.service.interfaces;

import com.moro.model.Beer;
import com.moro.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * beer service interface.
 */
@Service
public interface BeerService {

    /**
     * Get beers collection.
     *
     * @return beers collection.
     */
    Collection<BeerDto> getAllBeers();

    /**
     * Get beer By Id.
     *
     * @param beerId id.
     * @return beer.
     */
    Beer getBeerById(Integer beerId);

    /**
     * Persist new beer.
     *
     * @param beer new beer.
     * @return beer with id.
     */
    Beer addBeer(Beer beer);

    /**
     * Update beer.
     *
     * @param beer beer.
     */
    void updateBeer(Beer beer);

    /**
     * Delete beer.
     *
     * @param beerId beer id.
     */
    void deleteBeerById(Integer beerId);
}
