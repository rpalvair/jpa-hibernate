package com.palvair.tuto.orm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> age;
	public static volatile SingularAttribute<User, Long> ID;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> firstname;

}
