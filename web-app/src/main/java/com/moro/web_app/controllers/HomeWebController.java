package com.moro.web_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {

    /**
     * Redirect to default page -> beerfactory
     */
    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:beerfactory";
    }


    @GetMapping(value = "/beerfactory")
    public final String beerfactoryPage() {
        return "beerfactory";
    }

}
