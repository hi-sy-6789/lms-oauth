package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.Book;
import com.pranav.oauthresourceserver.entity.Category;
import com.pranav.oauthresourceserver.model.BookModel;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    List<Book> getByCategory(Category category);

    List<Book> geAvailableByCategory(Category category);

    String addBook(BookModel bookModel);

    String editBook(Long id, BookModel bookModel);

    String deleteBookById(Long id);

    List<Book> get(List<Long> bookIds);

    Book save(Book book);

    Long getTotalCount();

    Long getTotalIssuedBooks();
}
