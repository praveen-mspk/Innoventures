package com.issuetracking.app.dto;

import com.issuetracking.app.enums.Status;
import jakarta.validation.constraints.NotNull;

public class IssueStatusRequestDTO {

    @NotNull(message = "Status cannot be null")
    private Status status;

    @NotNull(message = "User ID is required")
    private Long userId;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}