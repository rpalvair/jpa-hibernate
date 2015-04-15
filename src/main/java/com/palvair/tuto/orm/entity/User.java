package com.palvair.tuto.orm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long ID;

    @Column(name = "VERSION")
    @Version
    @Getter
    @Setter
    private Integer version;

    @Setter
    private String firstname;

    @Setter
    private String lastname;

    @Setter
    private String age;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Contact> contact;

    @ManyToOne(fetch = FetchType.LAZY)
    //optional
    @JoinColumn(name="MEETING_ID")
    private Meeting meeting;

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
