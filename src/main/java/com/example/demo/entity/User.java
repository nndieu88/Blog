package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String address;

    private String phone;

    private String avatar;

    private String email;

    private String password;

    private Date dateCreated;

    private Date dateUpdated;

    @ManyToOne
    @JoinColumn
    private Role role;

    public User(Long id, String userName, String address, String phone, String email, String password, Role role) {
        this.id = id;
        this.userName = userName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
