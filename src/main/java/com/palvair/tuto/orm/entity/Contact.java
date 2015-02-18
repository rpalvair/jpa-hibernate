package com.palvair.tuto.orm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rpalvair
 */
@Entity
@Table(name = "contact")
@Data
public class Contact {

    @Id
    @GeneratedValue
    private Long ID;
}
