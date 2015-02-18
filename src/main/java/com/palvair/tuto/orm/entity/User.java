package com.palvair.tuto.orm.entity;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long ID;

    @Setter
    private String firstname;

    @Setter
    private String lastname;

    @Setter
    private String age;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Contact> contact;

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {

    }

    public User(String firstname) {
        this.firstname = firstname;
    }
}
