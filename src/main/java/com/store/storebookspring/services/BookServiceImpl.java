package com.store.storebookspring.services;

import com.store.storebookspring.beans.BookItem;
import com.store.storebookspring.repository.BookRepository;
import com.store.storebookspring.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Book Service Implements
 */
@Service
public class BookServiceImpl implements BookService{

    /**
     * object to write/read with table product in db
     */
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void saveBook(Book book){
        book.setImage( book.getImage().trim() );
        book.setBook_name( book.getBook_name().trim() );
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Book book){
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBySearch(String search){
        return bookRepository.findByKeyword(search);
    }

    @Override
    @Transactional
    public List<Book> getBestFiveDiscount(){
        return bookRepository.getFirst5ByDiscount();
    }

    @Override
    @Transactional
    public Optional<Book> getBookById(long id){
        return bookRepository.findById(id);
    }

    @Override
    public void decrementQuantity(long id, int amount) {
        Optional<Book> book = bookRepository.findById(id);

        book.get().setQuantity( book.get().getQuantity() - amount );
        this.saveBook(book.get());
    }

    @Override
    @Transactional
    public void paymentCart(List<BookItem> items){

        for(BookItem item : items){
            decrementQuantity(item.getBookId(), item.getQuantity());
        }
    }
}