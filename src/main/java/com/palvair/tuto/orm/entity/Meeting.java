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
@Table(name = "meeting")
@Data
public class Meeting {

    @Id
    @GeneratedValue
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


    @OneToMany(fetch = FetchType.LAZY)
    private List<User> users;

}
