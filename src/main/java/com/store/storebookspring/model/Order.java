package com.store.storebookspring.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Orders entity DB
 */
@Entity
@Table(name="orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @JoinColumn(name = "user_name")
    private String user_name;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Timestamp created_date;

    @Column(name = "amount", nullable = false, precision = 10, scale = 1)
    private BigDecimal amount;

    /**
     * Order constructor
     * @param user_name string
     * @param created_date timestamp
     * @param amount big decimal
     */
    public Order(String user_name, Timestamp created_date, BigDecimal amount){
        this.user_name = user_name;
        this.created_date = created_date;
        this.amount = amount;
    }

    /**
     * Order empty constructor
     */
    public Order() {

    }

    /**
     * get id order
     * @return  long
     */
    public Long getId() {
        return id;
    }

    /**
     * set id order
     * @param id long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get user name
     * @return string
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * set user name
     * @param user_name string
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * get created date
     * @return timestamp
     */
    public Timestamp getCreated_date() {
        return created_date;
    }

    /**
     * set created date
     * @param created_date timestamp
     */
    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    /**
     * get amount order
     * @return big decimal
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * set amount
     * @param amount big decimal
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * order to string
     * @return string
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", created_date=" + created_date +
                ", amount=" + amount +
                '}';
    }
}
