package com.pranav.oauthresourceserver.repository;

import com.pranav.oauthresourceserver.entity.Book;
import com.pranav.oauthresourceserver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(Category category);

    List<Book> findByCategoryAndStatus(Category category, Integer status);

    Optional<Book> findByTag(String tag);
}
