package com.ASM.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.AccountDAO;
import com.ASM.dao.UserDAO;
import com.ASM.model.Account;
import com.ASM.model.User;
import com.ASM.service.SessionService;
@Controller
public class loginAdminController {
	@Autowired
	AccountDAO accDao;
	@Autowired
	UserDAO userDao;
	
	@Autowired
	SessionService session;
	
	@GetMapping("/loginad")
	public String login() {
		return "admin/loginad";
	}
	@PostMapping("/loginad")
	public String login(Model model, @RequestParam("email") String email,
			@RequestParam("password") String password) {
		try {
			Account acc = accDao.findByEmailJPQL(email);
			if (acc==null || !acc.getPassword().equals(password) || acc.isRole()==false) {
				model.addAttribute("message", "Invalid username or password!");
				return "admin/loginad";
			} else {
				User admin = userDao.findByEmail(email);
				session.set("adminacc",acc);
				session.set("admin", admin);
				model.addAttribute("admin",admin);
//				String uri = (String) session.get("security-uri");
//				if (uri != null) {
//					return "redirect:" + uri;
//					//return "admin/themxoasua"; // sua
//				} else {					
//					model.addAttribute("message", "Login succeed");
//					return "admin/home";
//				}
				return "redirect:admin/index";
			}
		} catch (Exception e) {
			model.addAttribute("message", "Invalid username");
			
		}
		return "admin/loginad";
	}
	@GetMapping("/logoutad")
	public String accountLogout() {
		User userLogin = (User)session.get("admin");
		if(userLogin != null)
			session.set("admin", null);
		return "admin/loginad";
	}
	@RequestMapping("/signupad")
	public String signup() {
		return "signUp";
	}
}
