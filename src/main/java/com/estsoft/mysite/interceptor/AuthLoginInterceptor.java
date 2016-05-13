package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired()
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		UserVo authUser = userService.login(userVo);
		String nextURL = request.getParameter("next");
		if(authUser == null){
			//System.out.println(request.getContextPath());   ?? 왜 잘돌아가는거지
			response.sendRedirect("/user/loginform?next="+nextURL );
			return false;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser );
		response.sendRedirect( request.getContextPath() + nextURL );
		return false;
	}
}
