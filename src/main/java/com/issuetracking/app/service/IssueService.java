package com.issuetracking.app.service;

import com.issuetracking.app.entity.Issue;
import java.util.List;

public interface IssueService {

    Issue createIssue(Issue issue);

    List<Issue> getAllIssues();
}