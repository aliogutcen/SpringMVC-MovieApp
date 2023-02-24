package com.ogutcenali.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String surName;
    @Column(length = 50)
    private String email;
    @Column(length = 15)
    private String phone;
    @Column(length = 32)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Movie> favMovies;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Genre> favGenres;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<MovieComment> comments;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserType userType=UserType.USER;



}
