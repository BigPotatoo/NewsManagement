package com.example.NewsManagement.model;

import com.example.NewsManagement.controller.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class News extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String title;

    @NonNull
    @Column(length = 50000)
    String text;

    @ManyToOne
    @JoinColumn(name = "id_userAccount", updatable = false)
    @NonNull
    @JsonIgnoreProperties(allowSetters = true, value = {"password", "username"})
    UserAccount userAccount;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "news", orphanRemoval = true)
//    @NonNull
//    List<Comment> commentList;
}