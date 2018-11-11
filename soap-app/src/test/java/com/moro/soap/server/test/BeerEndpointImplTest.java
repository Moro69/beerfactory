package com.moro.soap.server.test;

import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.service.interfaces.BeerService;
import com.moro.soap.implementation.BeerEndpointImpl;
import com.moro.soap.interfaces.BeerEndpoint;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soap-spring-test.xml"})
public class BeerEndpointImplTest {

    @Autowired
    private BeerEndpointImpl endpoint;
    @Autowired
    private BeerService service;

    private BeerDto beerDto1;
    private BeerDto beerDto2;
    private Beer beer;

    @Before
    public void setUp() {
        beerDto1 = new BeerDto(1, "beerDto1",
                4.5, "beer dto 1", 10, 3.3 , "beerDto1");

        beerDto2 = new BeerDto(2, "beerDto2",
                4.7, "beer dto 2", 20, 5.0 , "beerDto2");

        beer = new Beer("title", 5.0, "beer", 15, "title");
        beer.setBeerId(1);
    }

    @After
    public void tearDown() {
        verify(service);
        reset(service);
    }

    @Test
    public void getAllBeers() {
        ArrayList<BeerDto> beerDtos = new ArrayList<>(Arrays.asList(beerDto1, beerDto2));

        expect(service.getAllBeers())
                .andReturn(beerDtos);
        replay(service);

        Collection<BeerDto> beers = endpoint.getAllBeers();
        Assert.assertEquals(beers, beerDtos);

    }

    @Test
    public void addBeer() {
        expect(service.addBeer(beer))
                .andReturn(beer);
        replay(service);

        Beer newBeer = endpoint.addBeer(beer);
        Assert.assertEquals(newBeer, beer);
    }

    @Test
    public void getBeerById() {
        expect(service.getBeerById(beer.getBeerId()))
                .andReturn(beer);
        replay(service);

        Beer beerById = endpoint.getBeerById(beer.getBeerId());
        Assert.assertEquals(beerById, beer);
    }

    @Test
    public void updateBeer() {
        service.updateBeer(beer);
        expectLastCall();
        replay(service);

        endpoint.updateBeer(beer);
    }

    @Test
    public void deleteBeerById() {
        service.deleteBeerById(beer.getBeerId());
        expectLastCall();
        replay(service);

        endpoint.deleteBeerById(beer.getBeerId());
    }
}
