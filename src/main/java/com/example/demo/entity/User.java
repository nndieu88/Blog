package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    private String userName;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    private String address;

    private String phone;

    private String avatar;

    @Field(termVector = TermVector.YES, analyze= Analyze.YES, store= Store.NO)
    private String email;

    private String password;

    private Date dateCreated;

    private Date dateUpdated;

    @ManyToOne(fetch = FetchType.EAGER)
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
