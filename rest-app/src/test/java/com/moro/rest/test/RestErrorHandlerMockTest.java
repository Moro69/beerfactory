package com.moro.rest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.model.Beer;
import com.moro.rest.BeerRestController;
import com.moro.rest.RestErrorHandler;
import com.moro.service.interfaces.BeerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-mock-test.xml"})
@Rollback
public class RestErrorHandlerMockTest {

    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerRestController beerRestController;

    @Autowired
    private RestErrorHandler restErrorHandler;

    private MockMvc mockMvc;

    private Beer beer1;
    private Beer beer2;

    @Before
    public void setUp() {

        beer1 = new Beer("title1", 5.0, "beer1", 15, "image1");
        beer1.setBeerId(1);

        beer2 = new Beer("title2", 5.0, "beer2", 15, "image2");
        beer2.setBeerId(1);

        mockMvc = MockMvcBuilders.standaloneSetup(beerRestController)
                .setControllerAdvice(restErrorHandler)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void emptyResultDataAccessException() throws Exception {
        expect(beerService.getBeerById(anyInt())).andThrow(new EmptyResultDataAccessException("Invalid id", 1));
        replay(beerService);

        mockMvc.perform(get("/beerfactory/beers/{beerId}", 5))
                .andExpect(status().isNotFound())
                .andExpect(content().string("\"EmptyResultDataAccessException: Invalid id.\""));

        verify(beerService);
        reset(beerService);
    }

    @Test
    public void duplicateKeyException() throws Exception {
        expect(beerService.addBeer(anyObject())).andReturn(beer1);

        expect(beerService.addBeer(anyObject())).andThrow(new DuplicateKeyException("beer with that name is already exist."));

        replay(beerService);

        mockMvc.perform(post("/beerfactory/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(beer1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/beerfactory/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(beer2)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"DuplicateKeyException: beer with that name is already exist.\""));

        verify(beerService);
        reset(beerService);
    }
}
