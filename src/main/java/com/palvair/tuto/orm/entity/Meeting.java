package com.palvair.tuto.orm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author rpalvair
 */
@Entity
@Table(name = "Meeting")
@Data
public class Meeting {

    public Meeting() {

    }

    @Id
    @GeneratedValue
    @Getter
    private Long ID;

    @Column(name = "VERSION")
    @Version
    @Getter
    @Setter
    private Integer version;

    @Column
    @Getter
    @Setter
    private String name;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meeting",cascade = javax.persistence.CascadeType.PERSIST)
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;
}
