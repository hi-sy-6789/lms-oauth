package com.pranav.oauthresourceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueModel {

    @NotNull(message = "userId can not be null")
    @NotBlank(message = "userId can not be empty")
    Long userId;

    @NotNull(message = "Book Ids can not be null")
    @NotBlank(message = "Book Ids can not be empty")
    Long[] bookIdsArray;
}
