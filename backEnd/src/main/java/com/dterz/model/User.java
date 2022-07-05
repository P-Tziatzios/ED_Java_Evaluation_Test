package com.dterz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CLIENT_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "u_Name")
    private String userName;

    @Column(name = "pass")
    private String pass;

    @Column(name = "f_Name")
    private String fistName;

    @Column(name = "s_Name")
    private String surName;

    @Column(name = "comments")
    private String comments;

    @Column(name = "super_admin")
    private boolean superAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new java.util.ArrayList<>();

    @ManyToMany
    @JoinTable(name = "USER_PERMISSION",
            joinColumns = {@JoinColumn(name = "FK_USER")},
            inverseJoinColumns = {@JoinColumn(name = "FK_PERMISSION")})
    private Set<Permission> permissions;

    @ManyToMany
    @JoinTable(name = "ACCOUNT_USER",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    private Set<Account> accounts = new LinkedHashSet<>();


}
