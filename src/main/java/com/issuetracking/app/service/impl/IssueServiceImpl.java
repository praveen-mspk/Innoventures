package com.issuetracking.app.service.impl;

import com.issuetracking.app.dto.IssueStatusRequestDTO;
import com.issuetracking.app.entity.Issue;
import com.issuetracking.app.entity.User;
import com.issuetracking.app.enums.Role;
import com.issuetracking.app.enums.Status;
import com.issuetracking.app.enums.IssueStatus;
import com.issuetracking.app.repository.IssueRepository;
import com.issuetracking.app.repository.UserRepository;
import com.issuetracking.app.service.IssueService;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueServiceImpl(IssueRepository issueRepository,
                            UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Issue updateIssueStatus(Long issueId, IssueStatusRequestDTO request) {

        // 1️⃣ Fetch Issue
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with id: " + issueId));

        // 2️⃣ Fetch User (who is performing action)
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        IssueStatus currentStatus = issue.getStatus();
        Status newStatus = request.getStatus();

        // 3️⃣ Status Transition Logic
        switch (currentStatus) {

            case OPEN:
                handleOpenState(issue, user, newStatus);
                break;

            case IN_PROGRESS:
                handleInProgressState(issue, user, newStatus);
                break;

            case RESOLVED:
                handleResolvedState(issue, user, newStatus);
                break;

            default:
                throw new RuntimeException("No transitions allowed from current status: " + currentStatus);
        }

        return issueRepository.save(issue);
    }

    private void handleOpenState(Issue issue, User user, Status newStatus) {

        if (newStatus != Status.IN_PROGRESS) {
            throw new RuntimeException("OPEN issue can only move to IN_PROGRESS");
        }

        // Only assigned developer can move to IN_PROGRESS
        if (!issue.getAssignee().getId().equals(user.getId())
                && user.getRole() != Role.DEVELOPER) {
            throw new RuntimeException("Only assigned developer can start this issue");
        }

        issue.setStatus(IssueStatus.IN_PROGRESS);
    }

    private void handleInProgressState(Issue issue, User user, Status newStatus) {

        if (newStatus != Status.RESOLVED) {
            throw new RuntimeException("IN_PROGRESS issue can only move to RESOLVED");
        }

        // Only ADMIN or TESTER can resolve
        if (user.getRole() != Role.ADMIN &&
                user.getRole() != Role.TESTER) {
            throw new RuntimeException("Only ADMIN or TESTER can resolve this issue");
        }

        issue.setStatus(IssueStatus.RESOLVED);
    }

    private void handleResolvedState(Issue issue, User user, Status newStatus) {

        if (newStatus == Status.CLOSED) {

            // Only ADMIN can close
            if (user.getRole() != Role.ADMIN) {
                throw new RuntimeException("Only ADMIN can close this issue");
            }

            issue.setStatus(IssueStatus.CLOSED);

        } else if (newStatus == Status.REOPEN) {

            issue.setStatus(IssueStatus.OPEN);

        } else {
            throw new RuntimeException("RESOLVED issue can move only to CLOSED or REOPEN");
        }
    }
}