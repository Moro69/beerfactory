package com.moro.rest;

import com.moro.model.Order;
import com.moro.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class OrderRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class);

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/beerfactory/orders")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Order> getOrders(@RequestParam(value = "fromDate", required = false) String fromDate,
                                             @RequestParam(value = "toDate", required = false) String  toDate) {

        if(fromDate != null && toDate != null) {
            LOGGER.debug("getOrdersByDate({}, {})", fromDate, toDate);

            return orderService.getOrdersByDate(fromDate, toDate);
        } else {
            LOGGER.debug("getAllOrders()");

            return orderService.getAllOrders();
        }

    }

    @PostMapping(value = "/beerfactory/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order addOrder(@RequestBody Order order) {
        LOGGER.debug("addOrder({})", order);

        return orderService.addOrder(order);
    }

    @GetMapping(value = "/beerfactory/orders/{orderId}")
    @ResponseStatus(HttpStatus.FOUND)
    public Order getOrderById(@PathVariable(value = "orderId") int orderId) {
        LOGGER.debug("getOrderById({})", orderId);

        return orderService.getOrderById(orderId);
    }

    @DeleteMapping(value = "/beerfactory/orders/{orderId}")
    @ResponseStatus(HttpStatus.FOUND)
    public  void deleteOrderById(@PathVariable(value = "orderId") int orderId) {
        LOGGER.debug("deleteOrderById({})", orderId);

        orderService.deleteOrderById(orderId);
    }
}
