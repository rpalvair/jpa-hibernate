package com.palvair.tuto.orm.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Conference.class)
public abstract class Conference_ {

	public static volatile ListAttribute<Conference, Meeting> meetings;
	public static volatile SingularAttribute<Conference, Long> ID;

}

