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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Role role;
}
