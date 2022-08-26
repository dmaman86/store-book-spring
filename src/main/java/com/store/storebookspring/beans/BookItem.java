package com.store.storebookspring.beans;

import com.store.storebookspring.model.Book;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * book item is object added to basket
 */
public class BookItem implements Serializable {

    private static final long serialVersionUID = 1L;

    // book id add to basket
    private Long bookId;

    // book object add to basket
    private Book book;

    // quantity user which buy
    private int quantity;

    // string to display available
    private String observation = "";

    public BookItem(Book book){
        this.bookId = book.getId();
        this.book = book;
        this.quantity = 1;
    }

    /**
     *
     * @return long
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     *
     * @param bookId long
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     *
     * @return book object
     */
    public Book getBook() {
        return book;
    }

    /**
     *
     * @param book object
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     *
     * @return integer
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity integer
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * get a total price for this book
     * @return big decimal value amount
     */
    public BigDecimal getTotal(){
        double amount = 0.0;

        if(book != null){
            amount = ((book.getPrice().doubleValue()
                    - book.getPrice().doubleValue() *
                    (book.getDiscount().doubleValue() / 100)) * quantity);
        }
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public void incrementQuantity(){
        quantity++;
    }

    public void decrementQuantity(){
        if(quantity > 0)
            quantity--;
    }

    /**
     *
     * @return string
     */
    public String getObservation() {
        return observation;
    }

    /**
     *
     * @param observation string
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "OrderItem [product=" + book + ", quantity=" + quantity
                + ", getProduct()=" + getBook() + ", getQuantity()="
                + getQuantity() + ", getTotal()=" + getTotal()
                + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }
}
