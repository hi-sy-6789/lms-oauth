package com.pranav.springsecurityclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {
    @NotNull(message = "Please enter book title")
    @NotBlank(message = "Please enter book title")
    private String title;

    @NotNull(message = "Please enter book tag")
    @NotBlank(message = "Please enter book tag")
    private String tag;

    @NotNull(message = "Please enter book authors")
    @NotBlank(message = "Please enter book authors")
    private String author;

    @NotNull(message = "Please select category")
    private long categoryId;
}
