package com.moro.camel.integration.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.camel.integration.factory.EndpointFactory;
import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.soap.beersoapservice.BeerEndpoint;
import org.apache.camel.Exchange;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class BeerSoapManager {

    private BeerEndpoint beerEndpoint;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerSoapManager.class);

    @Autowired
    public BeerSoapManager(EndpointFactory factory,
                           ModelMapper modelMapper,
                           ObjectMapper objectMapper) {
        beerEndpoint = factory.createBeerEndpoint();
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    public void addBeer(Exchange exchange) throws IOException {
        Beer beer = modelMapper.map(beerEndpoint.addBeer(
                objectMapper.readValue(
                        exchange.getIn().getBody(String.class),
                        com.moro.soap.beersoapservice.Beer.class)), Beer.class);

        exchange.getOut().setBody(beer);
        LOGGER.info("addBeer({})", beer);
    }

    public void deleteBeerById(Exchange exchange, int beerId){
        beerEndpoint.deleteBeerById(beerId);

        exchange.getOut().setBody("");
        LOGGER.info("deleteBeerById({})", beerId);
    }

    public void getAllBeers(Exchange exchange){
        Collection<BeerDto> beers = new ArrayList<>();
        for (com.moro.soap.beersoapservice.BeerDto beerDto : beerEndpoint.getAllBeers()) {
            beers.add(modelMapper.map(beerDto, BeerDto.class));
        }

        exchange.getOut().setBody(beers);
        LOGGER.info("getAllBeers({})", beers);
    }

    public void getBeerById(Exchange exchange, int beerId){
        exchange.getOut().setBody(
                modelMapper.map(beerEndpoint.getBeerById(beerId), Beer.class)
        );
        LOGGER.info("getBeerById({})", beerId);
    }

    public void updateBeer(Exchange exchange) throws IOException {
        beerEndpoint.updateBeer(
                objectMapper.readValue(
                        exchange.getIn().getBody(String.class),
                        com.moro.soap.beersoapservice.Beer.class)
        );
        exchange.getOut().setBody("");
        LOGGER.info("updateBeer({})");
    }
}
