package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.Book;
import com.pranav.oauthresourceserver.entity.IssuedBook;
import com.pranav.oauthresourceserver.repository.IssueBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssuedBookServiceImpl implements  IssuedBookService{
    @Autowired
    private IssueBookRepository issueBookRepository;
    @Override
    public int getCountByBook(Book book) {
        return 0;
    }

    @Override
    public void addNew(IssuedBook issuedBook) {
        issuedBook.setReturned( OAuthResourceServerConstants.BOOK_NOT_RETURNED );
        issueBookRepository.save(issuedBook);
    }

    @Override
    public void save(IssuedBook issuedBook) {
        issueBookRepository.save(issuedBook);
    }
}
