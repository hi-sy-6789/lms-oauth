package com.pranav.oauthresourceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
    @NotNull(message = "Please enter category name")
    @NotBlank(message = "Please enter category name")
    private String name;

    @NotNull(message = "Please enter category short name")
    @NotBlank(message = "Please enter category short name")
    @Length(max = 4, message = "Category shortName Must not exceed 4 characters.")
    private String shortName;

    @Length(max = 1000, message = " Category notes Must not exceed 1000 characters.")
    private String notes;

}
