package com.pranav.oauthresourceserver.event;

import com.pranav.oauthresourceserver.entity.User;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class AssignRoleToUserEvent extends ApplicationEvent {
    private User user;
    private  String applicationUrl;
    public AssignRoleToUserEvent(User user, String applicationUrl) {
        super(user);
        this.user=user;
        this.applicationUrl=applicationUrl;
    }
}
