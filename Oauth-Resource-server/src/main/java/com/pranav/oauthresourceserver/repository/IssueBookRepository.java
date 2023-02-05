package com.pranav.oauthresourceserver.repository;

import com.pranav.oauthresourceserver.entity.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueBookRepository extends JpaRepository<IssuedBook, Long> {
}
