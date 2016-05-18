package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.repository.GuestbookRepository;

@Service
@Transactional
public class GuestbookService {
	@Autowired
	GuestbookRepository guestbookRepository;

	public List<Guestbook> getMessageList() {
		return guestbookRepository.getList();
	}
	
	public boolean deleteMessage( Guestbook vo ) {
		return guestbookRepository.delete( vo );
	}
	
	public Object deleteMessage( Guestbook vo, boolean isAjax) {
		boolean isDeleted = guestbookRepository.delete( vo );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		if(isDeleted){
			map.put("data", vo.getNo() );
		}
		else{
			map.put("data", null );
		}
		return map; 
	}
	
	public boolean insertMessage( Guestbook vo ) {
		Long no = guestbookRepository.insert(vo);
		return no != 0;
	}
	
	public Map<String, Object> insertMessage( Guestbook vo, boolean isAjax ) {
		long no = guestbookRepository.insert(vo);
		vo = guestbookRepository.get(no);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo );
		return map;
	}
	
	public Map<String, Object> getList(int page){
		List<Guestbook> list = guestbookRepository.getList(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", list );
		return map;
	}
}