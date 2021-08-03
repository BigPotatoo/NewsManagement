package com.example.NewsManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(length = 50000)
    String text;

    @ManyToOne
    @JoinColumn(name = "id_userAccount", nullable = false, updatable = false)
    @JsonIgnoreProperties(allowSetters = true, value = {"password", "username"})
    @NonNull
    private UserAccount userAccount;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_news", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private News news;
}