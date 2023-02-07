package com.pranav.oauthresourceserver.controller;

import com.pranav.oauthresourceserver.entity.Book;
import com.pranav.oauthresourceserver.entity.Category;
import com.pranav.oauthresourceserver.model.BookModel;
import com.pranav.oauthresourceserver.service.BookService;
import com.pranav.oauthresourceserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

@RequestMapping(value = "/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = {"/", "/list"})
    public List<Book> all() {
        return bookService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/{id}/list")
    public List<Book> getBooksFromCategoryId(@PathVariable(name = "id") Long id) {
        Category category = categoryService.get(id);
        return bookService.getByCategory( category );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/{id}/available")
    public List<Book> getAvailableBooks(@PathVariable(name = "id") Long id) {
        Category category = categoryService.get(id);
        return bookService.geAvailableByCategory( category );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = {"/add", "/add/"})
    public String addBook(@RequestBody @Valid BookModel bookModel){
        return bookService.addBook(bookModel);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/edit/{id}")
    public String editBook(@PathVariable(name="id") Long id,
                           @RequestBody @Valid BookModel  bookModel){
        return bookService.editBook(id, bookModel);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/remove/{id}")
    public String removeBook(@PathVariable( name="id") Long id){
        return bookService.deleteBookById(id);
    }


}
