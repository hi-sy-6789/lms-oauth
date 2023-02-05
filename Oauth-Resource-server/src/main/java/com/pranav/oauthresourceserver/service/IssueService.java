package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.Issue;
import com.pranav.oauthresourceserver.entity.User;

import java.util.List;

public interface IssueService {
    Issue addNew(User user);

    Issue get(Long id);

    void save(Issue issue);

    List<Issue> getAllUnreturned();
}
