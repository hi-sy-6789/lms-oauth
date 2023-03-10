package com.pranav.oauthresourceserver.controller;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.*;
import com.pranav.oauthresourceserver.model.IssueModel;
import com.pranav.oauthresourceserver.service.BookService;
import com.pranav.oauthresourceserver.service.IssueService;
import com.pranav.oauthresourceserver.service.IssuedBookService;
import com.pranav.oauthresourceserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/issue")
public class IssueController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssuedBookService issuedBookService;

    @Value("${spring.message.personal.success}")
    String successMessage;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public List<Issue> listIssuePage() {
        return  issueService.getAllUnreturned();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String save(@RequestBody IssueModel issueModel) {

        Long userId = issueModel.getUserId();
        Long[] bookIdsArray = issueModel.getBookIdsArray();
        List<Long> bookIds = new ArrayList<>();

        for(Long itr:bookIdsArray){
            bookIds.add(itr);
        }
        User user = userService.get( userId );
        List<Book> books = bookService.get(bookIds);
        log.info(books+"    :::--->>>>> books");
        Issue issue = issueService.addNew(user);
        for( int itr=0 ; itr<books.size() ; itr++ ) {
            Book book = books.get(itr);
            book.setStatus( OAuthResourceServerConstants.BOOK_STATUS_ISSUED );
            book = bookService.save(book);
            IssuedBook issuedBook = new IssuedBook();
            issuedBook.setBook( book );
            issuedBook.setIssue( issue );
            issuedBookService.addNew( issuedBook );
        }

        return successMessage;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{id}/return/all", method = RequestMethod.GET)
    public String returnAll(@PathVariable(name = "id") Long id) {
        Issue issue = issueService.get(id);
        if( issue != null ) {
            List<IssuedBook> issuedBooks = issue.getIssuedBooks();
            for( int itr=0 ; itr<issuedBooks.size() ; itr++ ) {
                IssuedBook issuedBook = issuedBooks.get(itr);
                issuedBook.setReturned( OAuthResourceServerConstants.BOOK_RETURNED );
                issuedBookService.save( issuedBook );

                Book book = issuedBook.getBook();
                book.setStatus( OAuthResourceServerConstants.BOOK_STATUS_AVAILABLE );
                bookService.save(book);
            }

            issue.setReturned( OAuthResourceServerConstants.BOOK_RETURNED );
            issueService.save(issue);

            return successMessage;
        } else {
            return "Invalid Issue Id";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value="/{id}/return", method = RequestMethod.POST)
    public String returnSelected(@RequestBody IssueModel issueModel, @PathVariable(name = "id") Long id) {
        Issue issue = issueService.get(id);
        Long[] issuedBookIds = issueModel.getBookIdsArray();
        if( issue != null ) {
            List<IssuedBook> issuedBooks = issue.getIssuedBooks();
            for(IssuedBook ib:issuedBooks){

                if( Arrays.asList(issuedBookIds).contains( ib.getId()) ) {
                    ib.setReturned( OAuthResourceServerConstants.BOOK_RETURNED );
                    issuedBookService.save( ib );

                    Book book = ib.getBook();
                    book.setStatus( OAuthResourceServerConstants.BOOK_STATUS_AVAILABLE );
                    bookService.save(book);
                }
            }

            return successMessage;
        } else {
            return "Invalid Issue Id";
        }
    }
}
