package com.moro.soap.implementation;

import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.service.interfaces.BeerService;
import com.moro.soap.interfaces.BeerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.ArrayList;

@WebService(endpointInterface = "com.moro.soap.interfaces.BeerEndpoint",
            serviceName = "BeerProcess", portName = "BeerProcessPort",
            targetNamespace = "http://moro.com/soap/beersoapservice")
public class BeerEndpointImpl implements BeerEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerEndpointImpl.class);

    private BeerService beerService;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Override
    public ArrayList<BeerDto> getAllBeers() {
        LOGGER.debug("getAllBeers");
        ArrayList<BeerDto> beers = (ArrayList<BeerDto>) beerService.getAllBeers();

        return beers;
    }

    @Override
    public Beer getBeerById(Integer beerId) {
        LOGGER.debug("getBeerById({})", beerId);
        Beer beer = beerService.getBeerById(beerId);

        return beer;
    }

    @Override
    public Beer addBeer(Beer beer) {
        LOGGER.debug("addBeer({})", beer);
        Beer newBeer = beerService.addBeer(beer);
        return newBeer;
    }

    @Override
    public void updateBeer(Beer beer) {
        LOGGER.debug("updateBeer({})", beer);
        beerService.updateBeer(beer);
    }

    @Override
    public void deleteBeerById(Integer beerId) {
        LOGGER.debug("deleteBeerById({})", beerId);
        beerService.deleteBeerById(beerId);
    }
}
