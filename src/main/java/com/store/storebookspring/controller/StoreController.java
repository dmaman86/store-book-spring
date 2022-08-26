package com.store.storebookspring.controller;

import com.store.storebookspring.beans.BookItem;
import com.store.storebookspring.beans.CartData;
import com.store.storebookspring.model.Book;
import com.store.storebookspring.model.Order;
import com.store.storebookspring.model.Role;
import com.store.storebookspring.services.BookService;
import com.store.storebookspring.services.OrderService;
import com.store.storebookspring.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.unbescape.html.HtmlEscape;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class StoreController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Resource(name = "sessionBeanInit")
    private CartData cartData;

    @Resource(name = "applicationBeanInit")
    private CartData appCartData;

    private BookService getRepoBook(){
        return bookService;
    }

    private OrderService getRepoOrder(){
        return orderService;
    }

    private UserService getRepoUser(){ return userService; }

    /**
     *
     * @param model to add data to response
     * @return view store/index
     */
    @RequestMapping("/")
    public String home(Model model){
        appCartData.getBooksList().add(getRepoBook().getBestFiveDiscount().get(0));

        if(cartData.getSuccess()){
            cartData.setBooksMap(new ConcurrentHashMap<>());
            cartData.setQuantity(0);
            cartData.setTotal(0);
            cartData.setSuccess(false);
        }

        model.addAttribute("books", getRepoBook().getBestFiveDiscount());
        model.addAttribute("cart", cartData.getBooksList());
        model.addAttribute("quantity", cartData.getQuantity());
        return "store/index";
    }

    /**
     *
     * @param book object
     * @param model to add data to response
     * @param search string
     * @return view store/index
     */
    @PostMapping("/searchBook")
    public String searchBook(Book book, Model model, String search){

        if(search == null || search.isEmpty()){
            return "redirect:/";
        }

        model.addAttribute("cart", cartData.getBooksList());
        model.addAttribute("quantity", cartData.getQuantity());
        model.addAttribute("books", getRepoBook().getAllBySearch(search));
        return "store/index";
    }

    /**
     *
     * @param book object
     * @param model to add data to response
     * @return view store/index
     */
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("book")Book book, Model model){
        BookItem bookItem = new BookItem(book);

        if(cartData.contains(bookItem.getBookId()))
            cartData.incrementBookItemQuantity(bookItem.getBookId());
        else{
            cartData.addBook(bookItem.getBookId(), bookItem);
        }

        return "redirect:/";
    }

    /**
     *
     * @param model object
     * @return view store/checkout
     */
    @GetMapping("/checkOut")
    public String checkOutPage(Model model){

        List<BookItem> items = cartData.getOrderItemsList();
        validatedBooksInOrder(items);

        if(cartData.getTotalBigDecimal().doubleValue() == 0)
            model.addAttribute("error", "It is not possible to make a purchase with an account not larger than zero");

        model.addAttribute("carts", items);
        model.addAttribute("total", cartData.getTotalBigDecimal());

        if(isAuthenticated())
            return "redirect:/payBooks";

        return "store/checkout";
    }

    /**
     *
     * @param id identify
     * @param model object
     * @return view store/checkout
     */
    @PostMapping("/incrementProduct/{id}")
    public String incrementProduct(@PathVariable(value = "id") long id, Model model){
        cartData.incrementBookItemQuantity(id);
        if(isAuthenticated()){
            return "redirect:/payBooks";
        }
        return "redirect:/checkOut";
    }

    /**
     *
     * @param id identify
     * @param model to add data to response
     * @return view store/checkout
     */
    @PostMapping("/decrementProduct/{id}")
    public String decrementProduct(@PathVariable(value = "id") long id, Model model){
        cartData.decrementBookItemQuantity(id);
        if(isAuthenticated()){
            return "redirect:/payBooks";
        }
        return "redirect:/checkOut";
    }

    /**
     *
     * @param id identify
     * @param model to add data to response
     * @return view store/payment or store/checkout
     */
    @PostMapping("/deleteBookCart/{id}")
    public String deleteProductCart(@PathVariable(value = "id") long id, Model model){
        cartData.removeBook(id);
        if(isAuthenticated()){
            return "redirect:/payBooks";
        }
        return "redirect:/checkOut";
    }

    /**
     * private function
     * @param items list
     */
    private void validatedBooksInOrder(List<BookItem> items){
        for(BookItem bookItem : items){
            Optional<Book> book = getRepoBook().getBookById(bookItem.getBookId());
            if(book.isPresent()){
                if(book.get().getQuantity() < bookItem.getQuantity()){
                    if(book.get().getQuantity() == 0)
                        bookItem.setObservation("This book: " + book.get().getBook_name() + "is out of stock.");
                    else
                        bookItem.setObservation("Sorry but the quantity in stock for a book: "
                                + book.get().getBook_name() +
                                " is: " + book.get().getQuantity());
                }else{
                    bookItem.setObservation("");
                }
            }else{
                bookItem.setObservation("This book: " + bookItem.getBook().getBook_name() + " is not in stock.");
            }
        }
    }

    /**
     *
     * @param model to add data to response
     * @return view store/payment
     */
    @GetMapping("/payBooks")
    public String getPaymentPage(Model model){

        List<BookItem> items = cartData.getOrderItemsList();
        validatedBooksInOrder(items);

        if(cartData.getTotalBigDecimal().doubleValue() == 0)
            model.addAttribute("error",
                    "It is not possible to make a purchase with an amount not larger than zero");

        model.addAttribute("carts", items);
        model.addAttribute("total", cartData.getTotalBigDecimal());

        return "store/payment";
    }

    /**
     *
     * @param model to add data to response
     * @param principal session
     * @return view store/invoice or store/checkout
     */
    @PostMapping("/paybooks")
    public String makeBuy(Model model, Principal principal){
        try {

            if(cartData.getTotalBigDecimal().doubleValue() == 0)
                throw new Exception();
            getRepoBook().paymentCart(cartData.getOrderItemsList());
            cartData.setSuccess(true);
            return "redirect:/invoice";
        }catch(Exception e){
            return "redirect:/payBooks";
        }
    }

    /**
     *
     * @param model to add data to response
     * @param principal session
     * @return view store/invoice
     */
    @GetMapping("/invoice")
    public String getInvoicePage(Model model, Principal principal){

        Order order = new Order(principal.getName(), new Timestamp(System.currentTimeMillis()), cartData.getTotalBigDecimal());
        getRepoOrder().payment(order);

        model.addAttribute("order", order);
        model.addAttribute("carts", cartData.getOrderItemsList());
        model.addAttribute("total", cartData.getTotalBigDecimal());

        return "store/invoice";
    }

    /**
     *
     * @param model to add data to response
     * @return store/index
     */
    @PostMapping("/emptyBasket")
    public String emptyCart(Model model){

        cartData.clearCart();

        return "redirect:/";
    }

    /**
     * to logout
     * @param request http servlet request
     * @param principal session
     * @return view admin/index or store/index
     */
    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request, Principal principal){

        List<Role> roles = getRepoUser().getUserRoles(principal.getName());
        request.getSession().invalidate();

        if(roles.stream().anyMatch( role -> role.getName().equals("ADMIN"))){
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    /**
     * private function to authenticated user
     * @return boolean
     */
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /** Simulation of an exception. */
    @RequestMapping("/*")
    public void simulateError() throws ResponseStatusException{
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is a simulated error message");
    }

    /** Error page. */
    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(HtmlEscape.escapeHtml5(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error";
    }

    /** Error page. */
    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }

}