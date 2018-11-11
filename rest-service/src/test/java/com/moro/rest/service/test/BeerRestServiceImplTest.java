package com.moro.rest.service.test;

import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.rest.service.BeerRestServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-service-test.xml"})
public class BeerRestServiceImplTest {

    @Autowired
    private BeerRestServiceImpl beerRestService;
    @Autowired
    private RestTemplate restTemplateMock;

    private Beer beer;
    private BeerDto beerDto1;
    private BeerDto beerDto2;

    @Before
    public void setUp() {
        beerDto1 = new BeerDto(1, "beerDto1",
                4.5, "beer dto 1", 10, 3.3 , "beerDto1");
        beerDto2 = new BeerDto(2, "beerDto2",
                4.7, "beer dto 2", 20, 5.0 , "beerDto2");
        beer = new Beer("test", 5.0, "beer", 15, "image");
        beer.setBeerId(1);
    }

    @After
    public void tearDown() {
        verify(restTemplateMock);
        reset(restTemplateMock);
    }

    @Test
    public void addBeer() {
        expect(restTemplateMock.postForObject(anyString(), anyObject(), anyObject()))
                .andReturn(beer);
        replay(restTemplateMock);

        Beer beer1 = beerRestService.addBeer(beer);
        Assert.assertEquals(beer1, beer);

    }

    @Test
    public void getAllBeers( ){
        Collection<BeerDto> beers = Arrays.asList(beerDto1, beerDto2);
        ResponseEntity entity = new ResponseEntity(beers, HttpStatus.FOUND);

        expect(restTemplateMock.getForEntity(anyString(), anyObject()))
                .andReturn(entity);
        replay(restTemplateMock);

        Collection<BeerDto> dtos = beerRestService.getAllBeers();
        Assert.assertEquals(dtos, beers);
    }

    @Test
    public void getBeerById() {
        expect(restTemplateMock.getForObject(anyString(), anyObject(), anyObject(Map.class)))
                .andReturn(beer);
        replay(restTemplateMock);

        Beer beer1 = beerRestService.getBeerById(beer.getBeerId());
        Assert.assertEquals(beer1, beer);
    }

    @Test
    public void updateBeer() {
        restTemplateMock.put(anyString(), anyObject(), anyObject(Beer.class));
        expectLastCall();
        replay(restTemplateMock);

        beerRestService.updateBeer(beer);
    }

    @Test
    public void deleteBeerById() {
        restTemplateMock.delete(anyString());
        expectLastCall();
        replay(restTemplateMock);

        beerRestService.deleteBeerById(beer.getBeerId());
    }

}
