package com.issuetracking.app.controller;

import com.issuetracking.app.dto.ApiResponse;
import com.issuetracking.app.entity.Issue;
import com.issuetracking.app.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Issue>> createIssue(
            @Valid @RequestBody Issue issue) {

        Issue savedIssue = issueService.createIssue(issue);

        ApiResponse<Issue> res = new ApiResponse<>(
                LocalDateTime.now(),
                201,
                "Issue created successfully",
                savedIssue
        );

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Issue>>> getAllIssues() {

        List<Issue> issues = issueService.getAllIssues();

        ApiResponse<List<Issue>> res = new ApiResponse<>(
                LocalDateTime.now(),
                200,
                "Success",
                issues
        );

        return ResponseEntity.ok(res);
    }
}