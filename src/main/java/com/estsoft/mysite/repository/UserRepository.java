package com.estsoft.mysite.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.User;
import com.mysema.query.jpa.impl.JPAQuery;

import static com.estsoft.mysite.domain.QUser.user;

@Repository
public class UserRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void save( User user ) {
		em.persist( user );
	}

	public User get(String email) {
		return new JPAQuery( em ).from( user ).where( user.email.eq( email ) ).singleResult( user );
	}

	public User get(User authUser) {
		return new JPAQuery( em ).from( user ).where( user.email.eq( authUser.getEmail() ), user.password.eq( authUser.getPassword() ) ).singleResult( user );
	}
}
