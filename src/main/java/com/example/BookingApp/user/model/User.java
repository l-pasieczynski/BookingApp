package com.example.BookingApp.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @Column(unique = true)
    private Integer IDNumber;
    private String city;
    private boolean active;
    @ManyToMany
    private Set<Role> roles;
    private boolean vip;

}
