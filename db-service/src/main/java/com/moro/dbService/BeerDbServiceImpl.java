package com.moro.dbService;

import com.moro.dao.interfaces.BeerDao;
import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.service.interfaces.BeerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class BeerDbServiceImpl implements BeerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerDbServiceImpl.class);

    private final BeerDao beerDao;

    @Autowired
    public BeerDbServiceImpl(BeerDao beerDao) {
        this.beerDao = beerDao;
    }

    @Override
    public Collection<BeerDto> getAllBeers() {
        LOGGER.debug("getAllBeers()");

        return beerDao.getAllBeers();
    }

    @Override
    public Beer getBeerById(Integer beerId) {
        LOGGER.debug("getBeerById({})", beerId);

        return beerDao.getBeerById(beerId);
    }

    @Override
    public Beer addBeer(Beer beer) {
        LOGGER.debug("addBeer({})", beer);

        return beerDao.addBeer(beer);
    }

    @Override
    public void updateBeer(Beer beer) {
        LOGGER.debug("updateBeer({})", beer);

        beerDao.updateBeer(beer);
    }

    @Override
    public void deleteBeerById(Integer beerId) {
        LOGGER.debug("deleteBeerById({})", beerId);

        beerDao.deleteBeerById(beerId);
    }
}
