package com.issuetracking.app.service.impl;

import com.issuetracking.app.entity.Issue;
import com.issuetracking.app.entity.User;
import com.issuetracking.app.enums.IssueStatus;
import com.issuetracking.app.repository.IssueRepository;
import com.issuetracking.app.repository.UserRepository;
import com.issuetracking.app.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private static final Logger logger =
            LoggerFactory.getLogger(IssueServiceImpl.class);

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueServiceImpl(IssueRepository issueRepository,
                            UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Issue createIssue(Issue issue) {

        logger.info("Creating issue: {}", issue.getTitle());

        Long userId = issue.getCreatedBy().getId();

        User creator = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + userId));

        issue.setCreatedBy(creator);
        issue.setStatus(IssueStatus.OPEN);

        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getAllIssues() {

        logger.info("Fetching all issues");

        return issueRepository.findAll();
    }
}