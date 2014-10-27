package com.palvair.tuto.orm.entity;

import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
