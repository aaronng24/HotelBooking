package com.ASM.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	
	public Cookie get(String name) {
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equalsIgnoreCase(name))return cookie;
			}
		}
		return null;
	}
	
	public Cookie add(String name,String value,int hours) {
		Cookie cookie=new Cookie(name,value);
		cookie.setMaxAge(hours);
		cookie.setPath("/");
		return cookie;
	}
	
	public void remove(String name) {
		request.removeAttribute(name);
	}
}
