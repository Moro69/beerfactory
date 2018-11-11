package com.moro.camel.integration.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.camel.integration.factory.EndpointFactory;
import com.moro.model.Order;
import com.moro.soap.ordersoapservice.OrderEndpoint;
import org.apache.camel.Exchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class OrderSoapManager {
    private OrderEndpoint orderEndpoint;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderSoapManager(EndpointFactory factory,
                            ModelMapper modelMapper,
                            ObjectMapper objectMapper) {
        this.orderEndpoint = factory.createOrderEndpoint();
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    public void getAllOrders(Exchange exchange) {
        Collection<Order> orders = new ArrayList<>();
        for (com.moro.soap.ordersoapservice.Order order : orderEndpoint.getAllOrders()) {
            orders.add(modelMapper.map(order, Order.class));

            exchange.getOut().setBody(orders);
        }
    }
    public void getOrdersByDate(Exchange exchange, HttpServletRequest request) {
        Collection<Order> orders = new ArrayList<>();

        for (com.moro.soap.ordersoapservice.Order order :
                orderEndpoint.getOrdersByDate(request.getParameter("fromDate"),
                        request.getParameter("toDate"))) {
            orders.add(modelMapper.map(order, Order.class));
        }

        exchange.getOut().setBody(orders);
    }
    public void addOrder(Exchange exchange) throws IOException {
        exchange.getOut().setBody(
                modelMapper.map(orderEndpoint.addOrder(
                        objectMapper.readValue(
                                exchange.getIn().getBody(String.class),
                                com.moro.soap.ordersoapservice.Order.class)), Order.class)
        );
    }
    public void getOrderById(Exchange exchange, int orderId) {
        exchange.getOut().setBody(
                modelMapper.map(orderEndpoint.getOrderById(orderId), Order.class)
        );
    }
    public void deleteOrderById(Exchange exchange, int orderId) {
        orderEndpoint.deleteOrderById(orderId);
        exchange.getOut().setBody("");
    }
}
