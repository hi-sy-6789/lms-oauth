package com.pranav.oauthresourceserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private HttpStatus httpStatus;
    private String errorMessage;
}
