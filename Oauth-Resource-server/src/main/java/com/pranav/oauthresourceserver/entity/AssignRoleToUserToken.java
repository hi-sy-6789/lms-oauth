package com.pranav.oauthresourceserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleToUserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_ASSIGN_ADMIN_TOKEN"))
    private User user;

    public AssignRoleToUserToken(User user, String token) {
        super();
        this.token = token;
        this.user = user;
    }

    public AssignRoleToUserToken(String token) {
        super();
        this.token = token;
    }

}
