package com.store.storebookspring.services;

import com.store.storebookspring.model.Order;
import com.store.storebookspring.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    /**
     * object to write/read with table orders in db
     */
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void payment(Order order){
        orderRepository.save( order );
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findOrderByCreated();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllBySearch(String search){
        return orderRepository.findByKeyword(search);
    }

    @Override
    public BigDecimal getSumOrdersAmount(){
        return new BigDecimal(orderRepository.sumOrdersAmount()).setScale(2, RoundingMode.HALF_UP);
    }
}