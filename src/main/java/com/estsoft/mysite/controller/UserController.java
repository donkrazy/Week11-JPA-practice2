package com.estsoft.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( "/joinform" )
	public String joinform() {
		return "/user/joinform";
	}

	@RequestMapping( value="/join", method=RequestMethod.POST )
	public String join( @Valid @ModelAttribute User vo, BindingResult result, Model model) {
		// 에러 출력
		if ( result.hasErrors() ) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError e : list) {
			     System.out.println(" ObjectError : " + e );
			}
			model.addAllAttributes( result.getModel() );
	        return "/user/joinform";
		}
		User user = userService.getUser( vo.getEmail() );
		
		// email중복체크
		if(user!=null){
			return "/user/joinform";
		}
		
		userService.join(vo);
		return "/user/joinsuccess";
	}

	@RequestMapping( "/loginform" )
	public String loginForm() {
		return "/user/loginform";
	}
	
	@RequestMapping( "/checkemail" )
	@ResponseBody
	public Object checkEmail( @RequestParam( value="email", required=true, defaultValue="" ) String email ) {
		User user = userService.getUser( email );
		return userService.checkEmail(user, email);
	}
}
