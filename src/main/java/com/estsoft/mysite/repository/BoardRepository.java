package com.estsoft.mysite.repository;

import static com.estsoft.mysite.domain.QBoard.board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Board;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class BoardRepository {
	
	@PersistenceContext
	private EntityManager em;

	public Board get(long no) {
		Board vo = new JPAQuery( em ).from( board ).where( board.no.eq( no ) ).singleResult( board );
		return vo;
	}

	public void updateHits(long no) {
		 JPAUpdateClause updateClause = new JPAUpdateClause( em, board );
		 updateClause.where( board.no.eq( no ) ).set(board.hits, board.hits.add(1L)).execute();
	}

}
