package com.store.storebookspring.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Book entity DB
 */
@Entity
@Data
@Table(name="product")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Book empty constructor
     */
    public Book() {
    }

    /**
     * Book constructor
     * @param bookName  string
     * @param image string
     * @param quantity integer
     * @param price double
     * @param discount  double
     */
    public Book(String bookName, String image, Integer quantity, BigDecimal price, BigDecimal discount) {
        this.book_name = bookName.trim();
        this.price = price;
        this.quantity = quantity;
        this.image = image.trim();
        this.discount = discount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Book name is mandatory")
    private String book_name;

    private String image;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0 , message = "Quantity should be greater than zero")
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price should be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Discount is mandatory")
    @Min(value = 0, message = "The discount must be between 1 to 100")
    @Max(value = 100, message = "Discount must number be between 1 to 100")
    private BigDecimal discount;

    /**
     * get id book
     * @return id long
     */
    public Long getId() {
        return id;
    }

    /**
     * set id book
     * @param id long id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get book name
     * @return string
     */
    public String getBook_name() {
        return book_name;
    }

    /**
     * set book name
     * @param book_name string
     */
    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    /**
     * get image book
     * @return string
     */
    public String getImage() {
        return image;
    }

    /**
     * set book image
     * @param image string
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * get quantity book
     * @return integer
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * set quantity book
     * @param quantity integer
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * get price book
     * @return big decimal
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * set price book
     * @param price big decimal
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * get discount book
     * @return big decimal
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * set discount book
     * @param discount big decimal
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * book to string
     * @return string
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName=" + book_name +
                ", image=" + image +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount + '}';
    }
}