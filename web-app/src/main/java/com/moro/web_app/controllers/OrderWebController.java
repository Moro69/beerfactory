package com.moro.web_app.controllers;

import com.moro.model.BeerDto;
import com.moro.model.Order;
import com.moro.service.interfaces.BeerService;
import com.moro.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@Controller
public class OrderWebController {

    /**
     * Logger for order web controller.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderWebController.class);

    private final OrderService orderService;
    private final BeerService beerService;

    @Autowired
    public OrderWebController(OrderService orderService, BeerService beerService) {
        this.orderService = orderService;
        this.beerService = beerService;
    }

    @GetMapping(value = "/beerfactory/orders/neworder")
    @SuppressWarnings("unchecked")
    public final String addOrder(Model model) {
        LOGGER.debug("addOrder()");

        Order order = new Order();

        Collection<BeerDto> beers = beerService.getAllBeers();

        model.addAttribute("order", order);
        model.addAttribute("beers", beers);

        return "order";
    }

    @PostMapping(value = "/beerfactory/orders/neworder")
    @SuppressWarnings("unchecked")
    public final String addOrder(@Valid Order order,
                                BindingResult result,
                                Model model) {
        LOGGER.debug("addOrder({}), {}", order, result);

        if (result.hasErrors()) {
            Collection<BeerDto> beers = beerService.getAllBeers();

            model.addAttribute("beers", beers);
            return "order";

        } else {
            order.setOrderDate(LocalDate.now().toString());
            orderService.addOrder(order);

            return "redirect:/beerfactory/orders";
        }
    }


    @GetMapping(value = "/beerfactory/orders/delete/{orderId}")
    public final String deleteOrder(@PathVariable int orderId) {
        LOGGER.debug("deleteOrderById({})", orderId);

        orderService.deleteOrderById(orderId);

        return "redirect:/beerfactory/orders";
    }

}

