package com.pranav.oauthresourceserver.repository;

import com.pranav.oauthresourceserver.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByReturned(Integer bookNotReturned);
}
