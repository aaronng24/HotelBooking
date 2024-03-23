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

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(Model model, @ModelAttribute("account") Account account) {
		try {
			String email = account.getUser().getEmail();
			String password = account.getPassword();
			
			Account acc = accDao.findByEmailJPQL(email);
			User user1 = userDao.findByEmail(email);
			if (acc == null || !acc.getPassword().equals(password) || user1.equals("Inactive")) {
				model.addAttribute("message", "Sai thông tin đăng nhập!");
			} else {
				User user = userDao.findByEmail(email);
				session.set("user", user);
				session.set("useracc", acc);
				model.addAttribute("user", user);
//				String uri = (String) session.get("security-uri");
//				if (uri != null) {
//					return "redirect:" + uri;
//					//return "admin/themxoasua"; // sua
//				} else {					
//					model.addAttribute("message", "Login succeed");
//					return "admin/home";
//				}
				return "redirect:/home";
			}
		} catch (Exception e) {
			model.addAttribute("message", "Lỗi");
			e.printStackTrace();
		}
		return "user/login";
	}

	@GetMapping("/logout")
	public String accountLogout() {
		User userLogin = (User) session.get("user");
		if (userLogin != null)
			session.set("user", null);
			session.set("useracc", null);
		return "redirect:/home";
	}

}
