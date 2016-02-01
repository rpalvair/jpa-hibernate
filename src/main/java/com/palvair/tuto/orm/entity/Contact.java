package com.palvair.tuto.orm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author rpalvair
 */
@Entity
@Table(name = "Contact")
@Data
public class Contact {

    @Id
    @GeneratedValue
    private Long ID;

    @Column
    @Getter
    @Setter
    private String name;
}
