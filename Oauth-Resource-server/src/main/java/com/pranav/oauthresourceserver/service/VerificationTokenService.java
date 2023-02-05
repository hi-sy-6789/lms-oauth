package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.entity.VerificationToken;


import javax.servlet.http.HttpServletRequest;

public interface VerificationTokenService {
    void saveVerificationToken(User user, String token);

    String verifyToken(String token, HttpServletRequest httpServletRequest);

    VerificationToken generateNewVerificationTokenFromOldToken(String oldToken);
}
