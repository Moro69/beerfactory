package com.moro.web_app.controllers;

import com.moro.model.BeerDto;
import com.moro.model.Order;
import com.moro.model.Review;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
public class BeerfactoryWebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerfactoryWebController.class);

    private final BeerService beerService;
    private final OrderService orderService;

    @Autowired
    public BeerfactoryWebController(BeerService beerService, OrderService orderService) {
        this.beerService = beerService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/beerfactory/beers")
    public final String getAllBeers(Model model) {
        LOGGER.debug("getAllBeers()");

        Collection<BeerDto> beers = beerService.getAllBeers();

        Review review = new Review();

        model.addAttribute("beers", beers);
        model.addAttribute("review", review);

        return "beers";
    }

    @GetMapping(value = "/beerfactory/orders")
    public final String getOrders(Model model, @RequestParam(value = "fromDate", required = false) String fromDate,
                                  @RequestParam(value = "toDate", required = false) String  toDate) {
        Collection<Order> orders;

        if(fromDate != null && toDate != null) {
            LOGGER.debug("getOrdersByDate({}, {})", fromDate, toDate);
            orders = orderService.getOrdersByDate(fromDate, toDate);

        } else {
            LOGGER.debug("getAllOrders()");

            orders = orderService.getAllOrders();
        }

        model.addAttribute("orders", orders);

        return "orders";
    }

}
