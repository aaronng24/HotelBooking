package com.ASM.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.AccountDAO;
import com.ASM.dao.UserDAO;
import com.ASM.model.Account;
import com.ASM.model.User;
import com.ASM.service.EmailService;
import com.ASM.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller

public class LoginController {
	@Autowired
	AccountDAO accDao;
	@Autowired
	UserDAO userDao;

	@Autowired
	EmailService emailService;

	@Autowired
	SessionService session;

	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}

	
	@RequestMapping("/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message","Đăng nhập thành công!");
		return "redirect:/home";
	}

	@RequestMapping("/logout")
	public String accountLogout() {
		User userLogin = (User) session.get("user");
		if (userLogin != null)
			session.set("user", null);
			session.set("useracc", null);
		return "redirect:/home";
	}
	
	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		model.addAttribute("message","Sai thông tin đăng nhập!");
		return "user/login";
	}
	
	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message","Không có quyền truy xuất!");
		return "user/login";
	}
	@RequestMapping("/security/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message","Bạn đã đăng xuất!");
		return "user/login";
	}


}
