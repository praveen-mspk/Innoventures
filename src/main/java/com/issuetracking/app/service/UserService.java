package com.issuetracking.app.service;

import com.issuetracking.app.entity.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
}