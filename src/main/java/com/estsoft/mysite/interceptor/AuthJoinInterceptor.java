package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.service.UserService;

public class AuthJoinInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired()
	private UserService userService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		User authUser = userService.login(user);
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser );
	}
}
