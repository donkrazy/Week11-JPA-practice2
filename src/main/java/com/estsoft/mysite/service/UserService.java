package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void join( User user ) {
		userRepository.save(user);
	}
	
	public Map<String, Object> checkEmail(User user, String email){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", user == null );
		return map;
	}
	
	public User login( User user ) {
		User authUser = userRepository.get( user );
		return authUser;
	}
	
	public User getUser( String email ) {
		User user = userRepository.get( email );
		return user;
	}
}
