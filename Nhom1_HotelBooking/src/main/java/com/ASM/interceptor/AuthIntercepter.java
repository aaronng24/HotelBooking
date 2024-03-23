package com.ASM.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ASM.model.Account;
import com.ASM.model.User;
import com.ASM.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthIntercepter implements HandlerInterceptor {
	@Autowired
	SessionService session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		String uri = request.getRequestURI();
//		Account user = (Account) session.get("useracc"); // lấy từ session
//		//Account admin = (Account) session.get("adminacc");
//		String error = "";
//		
//		if ( uri.startsWith("/admin/")) {
//			if(user == null || !user.isRole()) {
//				error = "Access denied!";
//				session.set("security-uri", uri);
//				response.sendRedirect("/login?error=" + error);
//				return false;
//			}
//			
//		}
//		
//		else {
//			if(user==null) {
//				error = "please login!";
//                session.set("security-uri", uri);
//                response.sendRedirect("/login?error=" + error);
//                return false;
//			}
//		}
//		if (error.equals("Please login user!")) { // có lỗi
//			session.set("security-uri", uri);
//			response.sendRedirect("/login?error=" + error);
//			return false;
//		}else if (error.equals("Access denied!")) {
//			session.set("security-uri", uri);
//			response.sendRedirect("/loginad?error=" + error);
//			return false;
//		}
		return true;
	}
}
