package com.pranav.oauthresourceserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter category name")
    @NotBlank(message = "Please enter category name")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Please enter category short name")
    @NotBlank(message = "Please enter category short name")
    @Length(max = 4, message = "Category shortName Must not exceed 4 characters.")
    @Column(unique = true)
    private String shortName;

    @Length(max = 1000, message = " Category notes Must not exceed 1000 characters.")
    private String notes;

    private Date createDate;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;
}
