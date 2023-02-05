package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.Book;
import com.pranav.oauthresourceserver.entity.Category;
import com.pranav.oauthresourceserver.model.BookModel;
import com.pranav.oauthresourceserver.repository.BookRepository;
import com.pranav.oauthresourceserver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements  BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private  IssuedBookService issuedBookService;

    @Value("${spring.message.personal.success}")
    String successMessage;

    @Override
    public List<Book> getAll() {
        List<Book> list =  bookRepository.findAll();
        System.out.println(list+" listt  ");
        return list;
    }

    @Override
    public List<Book> getByCategory(Category category) {
        return bookRepository.findByCategory(category);
    }

    @Override
    public List<Book> geAvailableByCategory(Category category) {
        return bookRepository.findByCategoryAndStatus(category, OAuthResourceServerConstants.BOOK_STATUS_AVAILABLE);
    }

    @Override
    public String addBook(BookModel bookModel) {
        Book book
                = new Book();
        book.setTitle(bookModel.getTitle());
        book.setAuthor(bookModel.getAuthor());
        book.setTag(bookModel.getTag());
        book.setCategory(categoryRepository.findById(bookModel.getCategoryId()).get());
        book.setStatus(OAuthResourceServerConstants.BOOK_STATUS_AVAILABLE);
        book.setCreateDate(new Date());
        bookRepository.save(book);
        return successMessage;

    }

    @Override
    public String editBook(Long id, BookModel bookModel) {
        Optional<Book> optionalBook= bookRepository.findById(id);
        if(!optionalBook.isPresent())return "Invalid Book Id";
        if(!categoryRepository.findById(bookModel.getCategoryId()).isPresent()){
            return "Invalid Category Id";
        }
        Book book
                = optionalBook.get();
        book.setTitle(bookModel.getTitle());
        book.setTag(bookModel.getTag());
        book.setAuthor(bookModel.getAuthor());
        book.setCategory(categoryRepository.findById(bookModel.getCategoryId()).get());
        bookRepository.save(book);
        return successMessage;
    }

    @Override
    public String deleteBookById(Long id) {
        Optional<Book> optionalBook= bookRepository.findById(id);
        if(issuedBookService.getCountByBook(optionalBook.get())>0){
            return "Book in use";
        }
        bookRepository.deleteById(id);
        return successMessage;
    }

    @Override
    public List<Book> get(List<Long> bookIds) {
        return bookRepository.findAllById(bookIds);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Long getTotalCount() {
        return bookRepository.count();
    }

    @Override
    public Long getTotalIssuedBooks() {
        return bookRepository.count();
    }
}
