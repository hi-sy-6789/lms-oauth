package com.pranav.oauthresourceserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "id"
    )
    @NotNull
    private Book book;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    @NotNull
    private Issue issue;

    private Integer returned;
}
