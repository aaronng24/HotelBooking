package com.ASM.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AuthInterConfig implements WebMvcConfigurer{
//	@Autowired
//	AuthIntercepter auth;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(auth).addPathPatterns("/profile/**","/admin/**", "/vnpay","/thanhtoan")
//				.excludePathPatterns("/login", "/logout", "/signup","/forgotpass","/home","/hotel/detail/**","/hotel/list","/logoutad");
//	}
}
