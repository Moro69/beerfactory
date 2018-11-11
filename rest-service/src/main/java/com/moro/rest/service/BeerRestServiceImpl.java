package com.moro.rest.service;

import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.service.interfaces.BeerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeerRestServiceImpl implements BeerService {

    @Value("${beers.url}")
    private String beersUrl;

    private final static Logger LOGGER = LoggerFactory.getLogger(BeerRestServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    public BeerRestServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<BeerDto> getAllBeers() {
        LOGGER.debug("getAllBeers");
        ResponseEntity responseEntity =
                restTemplate.getForEntity(beersUrl, List.class);

        Collection<BeerDto> beers =
                (Collection<BeerDto>) responseEntity.getBody();

        return beers;
    }

    @Override
    public Beer getBeerById(Integer beerId) {
        LOGGER.debug("getBeerById({})", beerId);
        Map<String, Integer> params = new HashMap<>();
        params.put("beerId", beerId);
        Beer beer = restTemplate.getForObject(beersUrl + "/{beerId}", Beer.class, params);

        return beer;
    }

    @Override
    public Beer addBeer(Beer beer) {
        LOGGER.debug("addBeer({})", beer);
        Beer newBeer =
                restTemplate.postForObject(beersUrl, beer, Beer.class);

        return newBeer;
    }

    @Override
    public void updateBeer(Beer beer) {
        LOGGER.debug("updateBeer({})", beer);
        restTemplate.put(beersUrl + "/" + beer.getBeerId(), beer, Beer.class);
    }

    @Override
    public void deleteBeerById(Integer beerId) {
        LOGGER.debug("deleteBeerById({})", beerId);
        restTemplate.delete(beersUrl + "/" + beerId);
    }
}
