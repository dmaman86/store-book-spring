package com.store.storebookspring.beans;

import com.store.storebookspring.model.Book;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Basket sale
 */
@Component
public class CartData implements Serializable {

    private static final long serialVersion = 1L;

    // map to store book add to basket
    private Map<Long, BookItem> booksMap;

    // total transaction
    private double total;

    // quantity of books added
    private int quantity = 0;

    // flag to know user finish buy
    private Boolean success = false;

    /**
     * empty constructor
     */
    public CartData(){
        this.booksMap = new ConcurrentHashMap<Long, BookItem>();
    }

    /**
     *
     * @param bookId long
     * @param book book item object
     */
    public void addBook(Long bookId, BookItem book){
        this.booksMap.put(bookId, book);
        this.quantity++;
    }

    /**
     *
     * @param bookId long
     */
    public void removeBook(Long bookId){
        this.quantity -= this.booksMap.get(bookId).getQuantity();
        this.booksMap.remove(bookId);
    }

    /**
     *
     * @return list book item
     */
    public List<BookItem> getOrderItemsList(){
        return new ArrayList<BookItem>(booksMap.values());
    }

    /**
     *
     * @return list of book
     */
    public List<Book> getBooksList(){
        List<BookItem> bookItems = getOrderItemsList();
        List<Book> books = new ArrayList<>();

        for(BookItem bk : bookItems)
            books.add(bk.getBook());

        return books;
    }

    /**
     * get total of all basket
     * @return double total transaction
     */
    public BigDecimal getTotalBigDecimal(){
        total = 0.0;

        for(BookItem bk: booksMap.values())
            total += bk.getTotal().doubleValue();

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     *
     * @param bookId long
     * @return boolean
     */
    public boolean contains(Long bookId){
        return booksMap.containsKey(bookId);
    }

    /**
     *
     * @param bookId long
     */
    public void incrementBookItemQuantity(Long bookId){
        booksMap.get(bookId).incrementQuantity();
        this.quantity++;
    }

    /**
     *
     * @param bookId long
     */
    public void decrementBookItemQuantity(Long bookId){
        booksMap.get(bookId).decrementQuantity();
        this.quantity--;
    }

    /**
     *
     * @param id long
     * @return book item
     */
    public BookItem getProduct(Long id){
        return booksMap.get(id);
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
     * @param booksMap map
     */
    public void setBooksMap(Map<Long, BookItem> booksMap){
        this.booksMap = booksMap;
    }

    /**
     *
     * @param quantity integer
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     *
     * @param total integer
     */
    public void setTotal(int total){
        this.total = total;
    }

    /**
     *
     * @return boolean
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success boolean
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * clear values
     */
    public void clearCart(){
        this.booksMap.clear();
        this.quantity = 0;
        this.total = 0;
    }
}