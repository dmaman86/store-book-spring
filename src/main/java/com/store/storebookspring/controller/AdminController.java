package com.store.storebookspring.controller;

import com.store.storebookspring.beans.CartData;
import com.store.storebookspring.model.Book;
import com.store.storebookspring.services.BookService;
import com.store.storebookspring.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

/**
 * controller all request to admin zone
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * private member to manager book
     */
    @Autowired
    private BookService bookService;

    /**
     * private member to manager orders
     */
    @Autowired
    private OrderService orderService;

    @Resource(name = "applicationBeanInit")
    private CartData appCartData;

    private BookService getRepoBook(){
        return bookService;
    }

    private OrderService getRepoOrder(){
        return orderService;
    }

    /**
     * Administration zone index.
     * Note that we can access current logged user just by adding the Principal
     * parameter
     * @param principal session
     * @param model to add data to response
     * @return view admin/index
     */
    @RequestMapping("")
    public String adminIndex(Principal principal, Model model) {
        System.out.println("Current logged user details: " + principal.getName());

        appCartData.getBooksList().add(getRepoBook().getBestFiveDiscount().get(0));

        model.addAttribute("books", getRepoBook().getAllBooks());

        return "admin/index";
    }

    /**
     *
     * @param principal session
     * @param model to add data to response
     * @return view admin/payments
     */
    @RequestMapping("/payments")
    public String adminPayments(Principal principal, Model model){

        model.addAttribute("orders", getRepoOrder().getAllOrders());
        model.addAttribute("total_orders", getRepoOrder().getSumOrdersAmount());

        return "admin/payments";
    }

    /**
     *
     * @param book object
     * @param model to add data to response
     * @param search string
     * @return view admin/index
     */
    @PostMapping("/searchBook")
    public String searchBooks(Book book, Model model, String search){
        if(search == null || search.isEmpty()){
            return "redirect:/admin";
        }

        model.addAttribute("books", getRepoBook().getAllBySearch(search));
        return "admin/index";
    }

    /**
     *
     * @param book object
     * @param model to add data to response
     * @param search string
     * @return view admin/payments
     */
    @PostMapping("/searchOrder")
    public String searchOrders(Book book, Model model, String search){
        if(search == null || search.isEmpty()){
            return "redirect:/admin/payments";
        }

        model.addAttribute("orders", getRepoOrder().getAllBySearch(search));
        return "admin/payments";
    }

    /**
     *
     * @param id identify
     * @param book object
     * @param result binding result
     * @param model to add data to response
     * @return view admin/index
     */
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id,
                             @Valid Book book,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            book.setId(id);
            return "admin/update-book";
        }

        book.setId(id);
        if(book.getImage().trim().isEmpty())
            book.setImage( "/images/default_book_cover.jpeg" );

        try{
            getRepoBook().saveBook(book);
            return "redirect:/admin";
        }catch(Exception e){
            model.addAttribute("error", "Please check all fields.");
            return "admin/update-book";
        }
    }

    /**
     *
     * @param id identify
     * @param model to add data to response
     * @return view admin/update-book
     */
    @PostMapping("/editBook")
    public String editBook(@RequestParam("id") long id, Model model) {
        Book book = getRepoBook().getBookById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id)
        );

        model.addAttribute("book", book);
        return "admin/update-book";
    }

    /**
     *
     * @param book object
     * @param model to add data to response
     * @return view admin/add-book
     */
    @GetMapping( "/addBook")
    public String createBook(Book book, Model model) {
        return "admin/add-book";
    }

    /**
     *
     * @param book object
     * @param result binding result
     * @param model to add data to response
     * @return view admin/add-book
     */
    @PostMapping("/addBook")
    public String addBook(@Valid Book book,
                          BindingResult result,
                          Model model){

        if(result.hasErrors()){
            return "admin/add-book";
        }
        if(book.getImage().trim().isEmpty())
            book.setImage("/images/default_book_cover.jpeg");

        try{
            getRepoBook().saveBook(book);
            return "redirect:/admin";
        }catch(Exception e){
            model.addAttribute("error", "Please check all fields.");
            return "admin/add-book";
        }
    }

    /**
     *
     * @param id identify
     * @param model to add data to response
     * @return view admin/index
     */
    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {

        Book book = getRepoBook()
                .getBookById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        getRepoBook().deleteBook(book);
        model.addAttribute("books", getRepoBook().getAllBooks());
        return "redirect:/admin";
    }
}