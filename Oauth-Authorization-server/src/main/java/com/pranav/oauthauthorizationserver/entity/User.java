package com.pranav.oauthauthorizationserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "First name can not be left blank ")
    @NotNull(message = "First name can not be empty ")
    private String firstName;

    private String lastName;

    @NotBlank(message="Email can not be left blank")
    @NotNull(message = "Email can not be left empty")
    @Email(message = "Email has to be valid email", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password can not be left blank")
    @NotNull(message = "Password can not be left empty")
    @Column(length = 60)
    private String password;

    @NotNull
    private String role;

    @NotNull
    private boolean isEnabled;
}
