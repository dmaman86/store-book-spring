package com.store.storebookspring.services;

import com.store.storebookspring.model.Order;

import java.math.BigDecimal;
import java.util.List;

/**
 * Order Service DB
 */
public interface OrderService {

    void payment(Order order);

    public List<Order> getAllOrders();

    public List<Order> getAllBySearch(String search);

    public BigDecimal getSumOrdersAmount();
}