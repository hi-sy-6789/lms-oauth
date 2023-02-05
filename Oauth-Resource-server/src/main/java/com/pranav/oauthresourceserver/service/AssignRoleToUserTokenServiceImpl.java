package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.AssignRoleToUserToken;
import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.repository.AssignRoleToUserTokenServiceRepository;
import com.pranav.oauthresourceserver.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignRoleToUserTokenServiceImpl implements AssignRoleToUserTokenService {
    @Autowired
    private AssignRoleToUserTokenServiceRepository assignRoleToUserTokenServiceRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void saveAssignAdminToken(User user, String token) {
        AssignRoleToUserToken assignRoleToUserToken = new AssignRoleToUserToken(user, token);
        assignRoleToUserTokenServiceRepository.save(assignRoleToUserToken);
    }

    @Override
    public String verifyToken(String token) {
        Optional<AssignRoleToUserToken> optionalAssignRoleToUserToken = assignRoleToUserTokenServiceRepository.findByToken(token);

        if (!optionalAssignRoleToUserToken.isPresent()) {
            return "Invalid Token";
        }
        AssignRoleToUserToken assignRoleToUserToken= optionalAssignRoleToUserToken.get();

        User user = assignRoleToUserToken.getUser();

        assignRoleToUserTokenServiceRepository.delete(assignRoleToUserToken);
        user.setRole(OAuthResourceServerConstants.ROLE_ADMIN);
        userRepository.save(user);
        return "RoleAssigned";
    }
}
