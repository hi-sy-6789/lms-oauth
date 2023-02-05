package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.User;

import java.util.Optional;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);
}
