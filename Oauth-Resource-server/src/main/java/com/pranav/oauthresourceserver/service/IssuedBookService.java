package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.Book;
import com.pranav.oauthresourceserver.entity.IssuedBook;

public interface IssuedBookService {
    int getCountByBook(Book book);

    void addNew(IssuedBook issuedBook);

    void save(IssuedBook issuedBook);
}
