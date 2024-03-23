package com.ASM.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.UserDAO;
import com.ASM.model.Hotel;
import com.ASM.model.User;
import com.ASM.service.SessionService;

@Controller
@RequestMapping("/admin")
public class UserController {
	@Autowired
	UserDAO dao;
	@Autowired
	SessionService sessionService;
	
	@RequestMapping("/user")
	public String user(Model model,
	                   @RequestParam(name = "keyword", defaultValue = "") String keyword,
	                   @RequestParam(name = "page", defaultValue = "0") int page) {
	    PageRequest pageable = PageRequest.of(page, 2);
	    Page<User> userPage;

	    if (keyword.isEmpty()) {
	        userPage = dao.findAll(pageable);
	    } else {
	        userPage = dao.findAllByEmailLike("%" + keyword + "%", pageable);
	    }
	    User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
	    model.addAttribute("users", userPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", userPage.getTotalPages());
	    model.addAttribute("keyword", keyword);
	    return "admin/user";
	}

	@RequestMapping("/user-form/edit/{email}")
	public String editEmail(Model model, @PathVariable("email") String email) {
		User user = dao.findById(email).get();
		User useracc = (User) sessionService.get("user");
		model.addAttribute("useracc", useracc);
		model.addAttribute("user", user);
		return "admin/form/user-form";
	}
	
	@RequestMapping("/user-form/save")
	public String create(User user , Model model) {
		dao.save(user);
		User useracc = (User) sessionService.get("user");
		model.addAttribute("useracc", useracc);
		model.addAttribute("message","Cập nhật thành công");
		return "admin/form/user-form";
	}

	@GetMapping("/user-form")
	public String viewForm(@ModelAttribute("user")User user, Model model) {
		User useracc = (User) sessionService.get("user");
		model.addAttribute("user", useracc);
		return "admin/form/user-form";
	}
	
	@ModelAttribute("gender")
	public Map<String, String> getGender() {
		Map<String, String> map = new HashMap<>();
		map.put("Nam", "Nam");
		map.put("Nữ", "Nữ");
		map.put("Khác", "Giới tính khác");
		return map;
	}
	
	@ModelAttribute("status")
	public Map<String, String> getStatus() {
		Map<String, String> map = new HashMap<>();
		map.put("Active", "Active");
		map.put("Inactive", "Inactive");
		return map;
	}
}
