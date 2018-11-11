package com.moro.webapp.test;

import com.moro.model.Beer;
import com.moro.model.Review;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.ReviewService;
import com.moro.web_app.controllers.BeerWebController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:webapp-test-context.xml"})
@Rollback
public class BeerWebControllerTest {

    @Autowired
    private BeerWebController beerController;
    @Autowired
    private BeerService beerServiceMock;
    @Autowired
    private ReviewService reviewServiceMock;

    private MockMvc mockMvc;
    private Beer beer;
    private Review review;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("/WEB-INF/templates/");

        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setViewResolvers(viewResolver)
                .build();

        beer = new Beer("test", 5.0, "beer", 15, "image");
        beer.setBeerId(1);

        review = new Review(1, "good", 4.5);
        review.setReviewId(1);
    }

    @Test
    public void getEditBeer() throws Exception {
        expect(beerServiceMock.getBeerById(1))
                .andReturn(beer);
        replay(beerServiceMock);

        mockMvc.perform(get("/beerfactory/beers/edit/{beerId}", beer.getBeerId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("isNew"))
                .andExpect(model().attribute("isNew", false))
                .andExpect(model().attributeExists("beer"))
                .andExpect(model().attribute("beer", beer))
                .andExpect(view().name("beer"));

        verify(beerServiceMock);
        reset(beerServiceMock);
    }

    @Test
    public void postEditBeer() throws Exception {
        beerServiceMock.updateBeer(anyObject());
        expectLastCall();
        replay(beerServiceMock);

        MockMultipartFile image =
                new MockMultipartFile("image", "image.jpg", "image/jpeg", "some jpg".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/beerfactory/beers/edit/{beerId}", beer.getBeerId())
        .file(image)
        .param("beerId", beer.getBeerId().toString())
        .param("beerTitle", beer.getBeerTitle())
        .param("beerAbv", String.valueOf(beer.getBeerAbv()))
        .param("description", beer.getDescription())
        .param("price", String.valueOf(beer.getPrice()))
        .param("beerImage", beer.getBeerImage()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/beerfactory/beers"));

        verify(beerServiceMock);
        reset(beerServiceMock);
    }

    @Test
    public void getAddBeer() throws Exception {
        mockMvc.perform(get("/beerfactory/beers/newbeer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("isNew"))
                .andExpect(model().attribute("isNew", true))
                .andExpect(model().attributeExists("beer"))
                .andExpect(model().attribute("beer", new Beer()))
                .andExpect(view().name("beer"));
    }

    @Test
    public void postAddBeer() throws Exception {
        expect(beerServiceMock.addBeer(anyObject()))
                .andReturn(beer);
        replay(beerServiceMock);

        MockMultipartFile image =
                new MockMultipartFile("image", "image.jpg", "image/jpeg", "some jpg".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/beerfactory/beers/newbeer")
                .file(image)
                .param("beerId", beer.getBeerId().toString())
                .param("beerTitle", beer.getBeerTitle())
                .param("beerAbv", String.valueOf(beer.getBeerAbv()))
                .param("description", beer.getDescription())
                .param("price", String.valueOf(beer.getPrice()))
                .param("beerImage", beer.getBeerImage()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/beerfactory/beers"));

        verify(beerServiceMock);
        reset(beerServiceMock);
    }

    @Test
    public void deleteBeer() throws Exception {
        beerServiceMock.deleteBeerById(anyInt());
        expectLastCall();
        replay(beerServiceMock);

        mockMvc.perform(get("/beerfactory/beers/delete/{beerId}", beer.getBeerId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/beerfactory/beers"));
        verify(beerServiceMock);
        reset(beerServiceMock);

    }

    @Test
    public void addReview() throws Exception {
        expect(reviewServiceMock.addReview(anyObject()))
                .andReturn(review);
        replay(reviewServiceMock);

        mockMvc.perform(post("/beerfactory/beers/review")
        .param("reviewId", String.valueOf(review.getReviewId()))
        .param("beerId", String.valueOf(review.getBeerId()))
        .param("rating", String.valueOf(review.getRating()))
        .param("comment", review.getComment()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/beerfactory/beers"));

        verify(reviewServiceMock);
        reset(reviewServiceMock);

    }

}
