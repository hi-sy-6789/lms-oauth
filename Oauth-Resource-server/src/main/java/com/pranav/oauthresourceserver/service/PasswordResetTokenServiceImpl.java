package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.PasswordResetToken;
import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.repository.PasswordResetTokenRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken= new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "Invalid Token";
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Token Expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(token);
        User user = passwordResetToken.getUser();
        passwordResetTokenRepository.delete(passwordResetToken);
        return Optional.ofNullable(user);
    }
}
