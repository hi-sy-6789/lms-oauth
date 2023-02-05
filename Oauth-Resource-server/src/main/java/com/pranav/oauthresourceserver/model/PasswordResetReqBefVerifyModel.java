package com.pranav.oauthresourceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetReqBefVerifyModel {
    @NotBlank(message="Email can not be left blank")
    @NotNull(message = "Email can not be left empty")
    @Email(message = "Email has to be valid email", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotBlank(message = "Old Password can not be left blank")
    @NotNull(message = "Old Password can not be left empty")
    @Column(length = 60)
    private String oldPassword;

}
