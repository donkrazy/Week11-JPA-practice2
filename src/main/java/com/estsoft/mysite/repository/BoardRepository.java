package com.estsoft.mysite.repository;

import static com.estsoft.mysite.domain.QBoard.board;
import static com.estsoft.mysite.domain.QGuestbook.guestbook;
import static com.estsoft.mysite.domain.QUser.user;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.domain.Guestbook;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPADeleteClause;
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

	public void insert(Board vo) {
		vo.setRegDate( new Date() );
		if(vo.getGroupNo()==null){
			Long groupNo = new JPAQuery(em).from(board).singleResult(board.groupNo.max().add(1L));
			if(groupNo==null){
				groupNo=1L;
			}
			vo.setGroupNo(groupNo);
			vo.setOrderNo(1L);
			vo.setDepth(0);
			vo.setHits(0L);
		}
		em.persist(vo);
	}

	public void updateGroupOrder(Board vo) {
		JPAUpdateClause updateClause = new JPAUpdateClause( em, board );
		updateClause.where( board.groupNo.eq( vo.getGroupNo()), board.orderNo.goe(vo.getOrderNo()) ).set(board.orderNo, board.orderNo.add(1L)).execute();
	}

	public long getTotalCount(String keyword) {
		Long count = 0L;
		if(keyword==null || keyword.equals("")){
			count = new JPAQuery(em).from(board).join(board.user, user).count();
		}
		else{
			count = new JPAQuery(em).from(board).join(board.user, user).on(board.title.like("%"+keyword+"%").or(board.content.like("%"+keyword+"%"))).count();
		}
		return count;
	}

	public List<Board> getList(String keyword, long page, int sizeList) {
		JPAQuery query = new JPAQuery( em );
		SearchResults<Board> results = null;
		if(keyword==null || keyword.equals("")){
			results = query.from( board ).join(board.user, user).orderBy(board.groupNo.desc(), board.orderNo.asc() ).limit(sizeList).offset(page).listResults( board );
		}
		else{
			results = query.from( board ).join(board.user, user).where(board.title.like("%"+keyword+"%").or(board.content.like("%"+keyword+"%"))).orderBy(board.groupNo.desc(), board.orderNo.asc() ).limit(sizeList).offset(page).listResults( board );
		}
		return results.getResults();
	}

	public void update(Board guBoard) {
		JPAUpdateClause updateClause = new JPAUpdateClause( em, board );
		updateClause.where( board.no.eq( guBoard.getNo() ) ).set(board.title, guBoard.getTitle()).set(board.content, guBoard.getContent()).execute();
	}

	public void delete(Board vo) {
		Board result = new JPAQuery( em ).from( board ).where( board.no.eq( vo.getNo() ) ).singleResult( board );
		if(result==null){
			return;
		}
		em.remove(result);
	}
}
