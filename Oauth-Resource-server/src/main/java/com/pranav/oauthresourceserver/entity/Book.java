package com.pranav.oauthresourceserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter book title")
    @NotBlank(message = "Please enter book title")
    private String title;

    @NotNull(message = "Please enter book tag")
    @NotBlank(message = "Please enter book tag")
    @Column(unique = true)
    private String tag;

    @NotNull(message = "Please enter book authors")
    @NotBlank(message = "Please enter book authors")
    private String author;

    private Integer status;

    private Date createDate;

    @ManyToOne()
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Book_Category"))
    @NotNull(message = "Please select category")
    private Category category;
}
