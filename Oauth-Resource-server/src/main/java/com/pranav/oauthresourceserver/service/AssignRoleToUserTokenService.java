package com.pranav.oauthresourceserver.service;


import com.pranav.oauthresourceserver.entity.User;

public interface AssignRoleToUserTokenService {
    void saveAssignAdminToken(User user, String token);

    String verifyToken(String token);
}
