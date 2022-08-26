package com.store.storebookspring.services;

import com.store.storebookspring.beans.BookItem;
import com.store.storebookspring.model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Book Service DB
 */
public interface BookService {

    /**
     *
     * @return return list of books
     */
    public List<Book> getAllBooks();

    /**
     *
     * @param book object
     */
    public void saveBook(Book book);

    /**
     *
     * @param book object
     */
    public void deleteBook(Book book);

    /**
     *
     * @param search string
     * @return list of book object
     */
    public List<Book> getAllBySearch(String search);

    /**
     *
     * @return list of book object
     */
    public List<Book> getBestFiveDiscount();

    /**
     *
     * @param id long -> book id
     * @return book object
     */
    public Optional<Book> getBookById(long id);

    /**
     *
     * @param id book id
     * @param amount book amount select to purchase
     */
    public void decrementQuantity(long id, int amount);

    /**
     *
     * @param items list of book want to purchase
     */
    public void paymentCart(List<BookItem> items);
}