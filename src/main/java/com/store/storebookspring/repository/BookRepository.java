package com.store.storebookspring.repository;

import com.store.storebookspring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Book Repository interface
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * this function return list of books from DB
     * @param search string
     * @return list of book
     */
    @Query(value = "SELECT * FROM product p WHERE p.book_name LIKE %:search%", nativeQuery = true)
    List<Book> findByKeyword(@Param("search") String search);

    /**
     * This functions get 5 best book by discount from DB
     * @return list of book
     */
    @Query(value = "SELECT * FROM product p WHERE p.quantity > 0 ORDER BY p.discount DESC LIMIT 5", nativeQuery = true)
    List<Book> getFirst5ByDiscount();

    /**
     * This function return book from DB
     * @param name string
     * @return book object
     */
    @Query(value = "SELECT * FROM product p WHERE p.book_name = :name ", nativeQuery = true)
    Book findByName(@Param("name") String name);

}