package com.issuetracking.app.service;

import com.issuetracking.app.dto.IssueStatusRequestDTO;
import com.issuetracking.app.entity.Issue;

public interface IssueService {

    Issue updateIssueStatus(Long issueId, IssueStatusRequestDTO request);

}