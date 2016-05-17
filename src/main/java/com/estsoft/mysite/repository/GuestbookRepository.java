package com.estsoft.mysite.repository;

import static com.estsoft.mysite.domain.QGuestbook.guestbook;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Guestbook;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class GuestbookRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public Guestbook get( Long no ) {
		Guestbook vo = new JPAQuery( em ).from( guestbook ).where( guestbook.no.eq( no ) ).singleResult( guestbook );
		return vo;
	}
	
	public Long insert( Guestbook vo ) {
		vo.setRegDate( new Date() );
		em.persist( vo );
		return vo.getNo();
	}
	
	public boolean delete( Guestbook vo ) {
		Guestbook result = new JPAQuery( em ).from( guestbook ).where( guestbook.no.eq( vo.getNo() ), guestbook.password.eq( vo.getPassword() ) ).singleResult( guestbook );
		if( result == null ) {
			return false;
		}
		em.remove( result );
		return true;
	}
	
	public List<Guestbook> getList() {
		return new JPAQuery( em ).from( guestbook ).orderBy( guestbook.regDate.desc() ).list( guestbook );
	}

	public List<Guestbook> getList( int page ) {
		JPAQuery query = new JPAQuery( em );
		SearchResults<Guestbook> results = query.from( guestbook ).offset(5*(page-1)).limit(5).listResults( guestbook );
		return results.getResults();
	}
}
