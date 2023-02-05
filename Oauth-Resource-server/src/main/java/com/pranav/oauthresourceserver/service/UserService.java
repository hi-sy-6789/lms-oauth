package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.error.DuplicateEmailException;
import com.pranav.oauthresourceserver.error.InvalidEmailException;
import com.pranav.oauthresourceserver.model.UserModel;

import java.util.List;


public interface UserService {
    User registerUser(UserModel userModel) throws DuplicateEmailException;

    User findUserByEmail(String email) throws InvalidEmailException;

    boolean checkForValidPassword(User user, String password);

    void changeUserPassword(User user, String newPassword);

    List<User> getAllActiveMembers();

    User get(Long userId);

    Long getActiveUserTotalCount();
}
