package com.moro.rest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.model.Beer;
import com.moro.model.BeerDto;
import com.moro.model.Review;
import com.moro.rest.BeerRestController;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.ReviewService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-mock-test.xml"})
public class BeerRestControllerMockTest {

    @Autowired
    private BeerService beerService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BeerRestController beerRestController;

    private MockMvc mockMvc;

    private BeerDto beerDto1;
    private BeerDto beerDto2;

    private Beer beer;

    private Review review;

    @Before
    public void setUp() {
        beerDto1 = new BeerDto(1, "beerDto1",
                4.5, "beer dto 1", 10, 3.3 , "beerDto1");

        beerDto2 = new BeerDto(2, "beerDto2",
                4.7, "beer dto 2", 20, 5.0 , "beerDto2");

        beer = new Beer("title", 5.0, "beer", 15, "title");
        beer.setBeerId(1);

        review = new Review(1, "good", 4.5);
        review.setReviewId(1);

        mockMvc = MockMvcBuilders.standaloneSetup(beerRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void getBeerDtos() throws Exception {
        expect(beerService.getAllBeers())
                .andReturn(Arrays.asList(beerDto1, beerDto2));
        replay(beerService);

        mockMvc.perform(
                get("/beerfactory/beers")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].beerId", Matchers.is(1)))
                .andExpect(jsonPath("$[0].beerTitle", Matchers.is("beerDto1")))
                .andExpect(jsonPath("$[0].beerAbv", Matchers.is(4.5)))
                .andExpect(jsonPath("$[0].description", Matchers.is("beer dto 1")))
                .andExpect(jsonPath("$[0].beerPrice", Matchers.is(10)))
                .andExpect(jsonPath("$[0].rating", Matchers.is(3.3)))
                .andExpect(jsonPath("$[1].beerId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].beerTitle", Matchers.is("beerDto2")))
                .andExpect(jsonPath("$[1].beerAbv", Matchers.is(4.7)))
                .andExpect(jsonPath("$[1].description", Matchers.is("beer dto 2")))
                .andExpect(jsonPath("$[1].beerPrice", Matchers.is(20)))
                .andExpect(jsonPath("$[1].rating", Matchers.is(5.0)));

        verify(beerService);
        reset(beerService);
    }

    @Test
    public void getBeerById() throws Exception {
        expect(beerService.getBeerById(anyInt()))
                .andReturn(beer);
        replay(beerService);

        mockMvc.perform(get("/beerfactory/beers/{beerId}", beer.getBeerId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.beerId", Matchers.is(1)))
                .andExpect(jsonPath("$.beerTitle", Matchers.is("title")))
                .andExpect(jsonPath("$.beerAbv", Matchers.is(5.0)))
                .andExpect(jsonPath("$.description", Matchers.is("beer")))
                .andExpect(jsonPath("$.price", Matchers.is(15)));

        verify(beerService);
        reset(beerService);
    }

    @Test
    public void addBeer() throws Exception {
        expect(beerService.addBeer(anyObject()))
                .andReturn(beer);
        replay(beerService);

        mockMvc.perform(post("/beerfactory/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(beer))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("beerId", Matchers.is(1)))
                .andExpect(jsonPath("beerTitle", Matchers.is("title")))
                .andExpect(jsonPath("beerAbv", Matchers.is(5.0)))
                .andExpect(jsonPath("description", Matchers.is("beer")))
                .andExpect(jsonPath("price", Matchers.is(15)));

        verify(beerService);
        reset(beerService);
    }

    @Test
    public void updateBeer() throws Exception {
        beerService.updateBeer(anyObject());
        expectLastCall();
        replay(beerService);

        mockMvc.perform(put("/beerfactory/beers/{beerId}", beer.getBeerId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(beer))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(beerService);
        reset(beerService);
    }

    @Test
    public void deleteBeerById() throws Exception {
        beerService.deleteBeerById(beer.getBeerId());
        expectLastCall();
        replay(beerService);

        mockMvc.perform(
                delete("/beerfactory/beers/{beerId}", beer.getBeerId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());

        verify(beerService);
        reset(beerService);
    }

    @Test
    public void addReview() throws Exception {
        expect(reviewService.addReview(anyObject()))
                .andReturn(review);
        replay(reviewService);

        mockMvc.perform(post("/beerfactory/beers/review/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(review))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("reviewId", Matchers.is(1)))
                .andExpect(jsonPath("beerId", Matchers.is(1)))
                .andExpect(jsonPath("comment", Matchers.is("good")))
                .andExpect(jsonPath("rating", Matchers.is(4.5)));

        verify(reviewService);
        reset(reviewService);
    }
}
