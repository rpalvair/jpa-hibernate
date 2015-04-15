package com.palvair.tuto.orm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> firstname;
	public static volatile ListAttribute<User, Contact> contact;
	public static volatile SingularAttribute<User, Long> ID;
	public static volatile SingularAttribute<User, Integer> version;
	public static volatile SingularAttribute<User, Meeting> meeting;
	public static volatile SingularAttribute<User, String> age;
	public static volatile SingularAttribute<User, String> lastname;

}

