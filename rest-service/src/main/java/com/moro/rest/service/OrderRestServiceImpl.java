package com.moro.rest.service;

import com.moro.model.Order;
import com.moro.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

public class OrderRestServiceImpl implements OrderService {

    @Value("${orders.url}")
    private String ordersUrl;

    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestServiceImpl.class);


    @Autowired
    public OrderRestServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Order> getAllOrders() {
        LOGGER.debug("getAllOrders()");
        ResponseEntity responseEntity = restTemplate.getForEntity(ordersUrl, List.class);

        Collection<Order> orders =
                (Collection<Order>) responseEntity.getBody();
        return orders;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Order> getOrdersByDate(String fromDate, String toDate) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(ordersUrl)
                .queryParam("fromDate", fromDate)
                .queryParam("toDate", toDate);

        ResponseEntity responseEntity =
                restTemplate.getForEntity(builder.toUriString(), List.class);

        Collection<Order> orders =
                (Collection<Order>) responseEntity.getBody();

        return orders;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        LOGGER.debug("getOrderById({})", orderId);
        MultiValueMap<String, Integer> idParam = new LinkedMultiValueMap<>();
        idParam.add("ordeerId", orderId);

        Order order = restTemplate.getForObject(ordersUrl, Order.class, idParam);
        return order;
    }

    @Override
    public Order addOrder(Order order) {
        LOGGER.debug("addOrder({})", order);
        Order newOrder =
                restTemplate.postForObject(ordersUrl, order, Order.class);

        return newOrder;
    }

    @Override
    public void deleteOrderById(Integer orderId) {

        LOGGER.debug("deleteOrderById({})", orderId);
        restTemplate.delete(ordersUrl + "/" + orderId);
    }
}
