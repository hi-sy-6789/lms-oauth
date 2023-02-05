package com.pranav.oauthresourceserver.event.listener;

import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.event.AssignRoleToUserEvent;
import com.pranav.oauthresourceserver.service.AssignRoleToUserTokenService;
import com.pranav.oauthresourceserver.service.EmailSenderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AssignRoleToUserEventListener implements ApplicationListener<AssignRoleToUserEvent> {

    @Autowired
    private AssignRoleToUserTokenService assignRoleToUserTokenService;
    
    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void onApplicationEvent(AssignRoleToUserEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        assignRoleToUserTokenService.saveAssignAdminToken(user, token);

        String url = event.getApplicationUrl();
        String assignAdminUrl = url + "/assignAdminUser?token=" + token;

        log.info("Click the link to assign admin role to the user ", user.getEmail());
        emailSenderService.sendMailToAdminToAssignAdminRoleToUser(assignAdminUrl, user);
    }
}
