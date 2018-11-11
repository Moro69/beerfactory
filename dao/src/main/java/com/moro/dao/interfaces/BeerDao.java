package com.moro.dao.interfaces;

import com.moro.model.Beer;
import com.moro.model.BeerDto;

import java.util.Collection;

/**
 * beer DAO interface.
 */
public interface BeerDao {

    /**
     * Get collection of all beers.
     *
     * @return beer collection.
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
