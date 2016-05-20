package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.domain.Board;
import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.repository.BoardRepository;
import com.estsoft.utils.WebUtil;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	private static final int SIZE_LIST = 7;     // 리스팅되는 게시물의 수
	private static final int SIZE_PAGE = 5; // 페이지 리스트에서 표시되는 페이지 수
	
	public Map<String, Object> list(String keyword, String page){
		long currentPage = 1; 
		// 페이징 정보
		if( page != null && WebUtil.isNumeric( page ) ) {
			currentPage = Long.parseLong( page );
		}
		long totalCount = boardRepository.getTotalCount( keyword );
		long totalPage = (long)Math.ceil( (double)totalCount / SIZE_LIST );
		if( currentPage < 1 || currentPage > totalPage ) {
			currentPage = 1;
		}
		long firstPage = ( (long)Math.ceil( (double)currentPage / SIZE_PAGE  ) - 1 ) * SIZE_PAGE + 1;
		long lastPage = firstPage + SIZE_PAGE - 1;
		if( lastPage > totalPage ) {
			lastPage = totalPage;
		}
		long prevPage = 0;
		if( firstPage > SIZE_PAGE ) {
			prevPage = firstPage - 1;
		}
		long nextPage = 0;
		if( lastPage < totalPage ) {
			nextPage = lastPage + 1;
		}
		// 리스트 가져오기
		List<Board> list = boardRepository.getList( keyword, SIZE_LIST*(currentPage-1), SIZE_LIST  );

		// 포워딩
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "list", list );
		map.put( "totalCount",  totalCount);
		map.put( "sizeList",  SIZE_LIST );
		map.put( "keyword", keyword );
		map.put( "firstPage", firstPage );
		map.put( "lastPage", lastPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "currentPage", currentPage );
		return map;
	}
	
	
	public Board get(long no, boolean updatehits){
		if(updatehits){
			boardRepository.updateHits(no);
			Board board = boardRepository.get(no);
			return board;
		}
		else{
			Board board = boardRepository.get(no);
			return board;
		}
	}
	

	public void write( Board vo ){
		if( vo.getGroupNo() != null ) {
			vo.setOrderNo( vo.getOrderNo()  + 1 );
			vo.setDepth( vo.getDepth()  + 1 );
			vo.setHits(0L);
			boardRepository.updateGroupOrder( vo );
		}
		boardRepository.insert( vo );
	}


	public void update(Board board, Board guBoard) {
		guBoard.setTitle(board.getTitle());
		guBoard.setContent(board.getContent());
		boardRepository.update(guBoard);
	}


	public void delete(Long no, User authUser) {
		Board board = boardRepository.get(no);
		if(authUser.getNo()==board.getUser().getNo()){
			boardRepository.delete(board);
		}
	}
}
