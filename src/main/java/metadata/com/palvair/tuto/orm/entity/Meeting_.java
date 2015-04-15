package com.palvair.tuto.orm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Meeting.class)
public abstract class Meeting_ {

	public static volatile SingularAttribute<Meeting, Conference> conference;
	public static volatile SingularAttribute<Meeting, String> name;
	public static volatile SingularAttribute<Meeting, Long> ID;
	public static volatile SingularAttribute<Meeting, Integer> version;
	public static volatile ListAttribute<Meeting, User> users;

}

