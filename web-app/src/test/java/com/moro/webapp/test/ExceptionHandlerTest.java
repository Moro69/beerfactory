package com.moro.webapp.test;

import com.moro.model.Beer;
import com.moro.web_app.controllers.BeerWebController;
import com.moro.web_app.controllers.BeerfactoryExceptionHandler;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:webapp-test-context.xml"})
@Rollback
public class ExceptionHandlerTest {

    @Autowired
    private BeerfactoryExceptionHandler handler;
    @Autowired
    private BeerWebController beerController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("/WEB-INF/templates/");

        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setControllerAdvice(handler)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void handleIllegalArgumentException() throws Exception {
        MockMultipartFile image =
                new MockMultipartFile("image", "image.jpg", "text/plain", new byte[10]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/beerfactory/beers/newbeer")
                .file(image))
                .andDo(print())
                .andExpect(view().name("beer"))
                .andExpect(model().attribute("errorMessage", "Invalid image format. PNG and JPEG are only available."))
                .andExpect(model().attribute("beer", new Beer()));
    }

    @Test
    public void handleMaxSizeException() throws Exception {
        MockMultipartFile image =
                new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[2000001]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/beerfactory/beers/newbeer")
                .file(image)
                .param("beerTitle", "test")).andDo(print())
                .andExpect(view().name("beer"))
                .andExpect(model().attribute("errorMessage", "File size must be not larger than 2MB"))
                .andExpect(model().attribute("beer", new Beer()));
    }
}
