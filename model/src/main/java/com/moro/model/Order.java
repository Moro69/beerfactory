package com.moro.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class Order {


    private Integer orderId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String orderDate;

    @NotNull
    private Integer beerId;

    @PositiveOrZero(message = "Cannot be negative.")
    private int beerQuantity;

    private int orderPrice;

    public Order(String orderDate, int beerId, int beerQuantity, int orderPrice) {
        this.orderDate = orderDate;
        this.beerId = beerId;
        this.beerQuantity = beerQuantity;
        this.orderPrice = orderPrice;
    }

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getBeerId() {
        return beerId;
    }

    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }

    public int getBeerQuantity() {
        return beerQuantity;
    }

    public void setBeerQuantity(int beerQuantity) {
        this.beerQuantity = beerQuantity;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "Order{"
                + "orderId=" + orderId
                + ", orderDate=" + orderDate
                + ", beerId=" + beerId
                + ", beerQuantity=" + beerQuantity
                + ", orderPrice=" + orderPrice
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return beerQuantity == order.beerQuantity &&
                orderPrice == order.orderPrice &&
                Objects.equals(orderId, order.orderId) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(beerId, order.beerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, orderDate, beerId, beerQuantity, orderPrice);
    }
}
