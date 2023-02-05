package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.Issue;
import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.repository.IssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository issueRepository;
    @Override
    public Issue addNew(User user) {
        Issue issue= new Issue();
        issue.setUser(user);
        issue.setIssueDate( new Date() );
        log.info("okk11");
        issue.setReturned( OAuthResourceServerConstants.BOOK_NOT_RETURNED );
        log.info("okk22");
        log.info(issue.toString()+"  stringg  ");
        issueRepository.save(issue);
        return issue;
    }

    @Override
    public Issue get(Long id) {
        return issueRepository.findById(id).get();
    }

    @Override
    public void save(Issue issue) {
        issueRepository.save(issue);
    }

    @Override
    public List<Issue> getAllUnreturned() {
        return issueRepository.findByReturned(OAuthResourceServerConstants.BOOK_NOT_RETURNED);
    }
}
