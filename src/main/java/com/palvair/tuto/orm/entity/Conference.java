package com.palvair.tuto.orm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rpalvair
 */
@Entity
@Table(name = "Conference")
@Data
public class Conference {

    @Id
    @GeneratedValue
    private Long ID;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conference",cascade = javax.persistence.CascadeType.PERSIST)
    private List<Meeting> meetings = new ArrayList<>();
}
