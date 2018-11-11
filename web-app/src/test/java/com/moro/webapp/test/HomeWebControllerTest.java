package com.moro.webapp.test;

import com.moro.web_app.controllers.HomeWebController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:webapp-test-context.xml"})
@Rollback
public class HomeWebControllerTest {

    @Autowired
    private HomeWebController homeController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("/WEB-INF/templates/");

        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void defaultPageRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("redirect:beerfactory"));
    }

    @Test
    public void beerfactoryPage() throws Exception {
        mockMvc.perform(get("/beerfactory"))
                .andExpect(view().name("beerfactory"));
    }

}
