package com.store.storebookspring.repository;


import com.store.storebookspring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Order Repository interface
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * this function return list of orders
     * @param search string
     * @return list of Order
     */
    @Query(value = "SELECT * FROM orders WHERE orders.user_name LIKE %:search% OR orders.id LIKE %:search%", nativeQuery = true)
    List<Order> findByKeyword(@Param("search") String search);

    /**
     * This functions sum all amount orders
     * @return double
     */
    @Query(value = "SELECT SUM(amount) FROM orders", nativeQuery = true)
    double sumOrdersAmount();

    /**
     * find By Order By Date Desc
     * @return list orders
     */
    @Query(value = "SELECT * FROM orders ORDER BY orders.created_date DESC", nativeQuery = true)
    List<Order> findOrderByCreated();
}