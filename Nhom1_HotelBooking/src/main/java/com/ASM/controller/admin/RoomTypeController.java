package com.ASM.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.RoomTypeDAO;
import com.ASM.model.RoomType;
import com.ASM.model.User;
import com.ASM.service.SessionService;

@Controller
@RequestMapping("/admin")
public class RoomTypeController {
	@Autowired
	RoomTypeDAO roomTypeDAO;
	@Autowired
	SessionService sessionService;
	
	@RequestMapping("/roomtype")
	public String roomtype(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		RoomType roomType = new RoomType();
		model.addAttribute("roomType", roomType);
		Pageable pageable = PageRequest.of(page, 5);
		Page<RoomType> RTpage;
		if (keyword.isEmpty()) {
			RTpage = roomTypeDAO.findAll(pageable);
		} else {
			RTpage = roomTypeDAO.findByTypeName("%" + keyword + "%", pageable);
		}
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		model.addAttribute("page", RTpage);
		model.addAttribute("roomTypes", RTpage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", RTpage.getTotalPages());
		model.addAttribute("keyword", keyword);
		return "admin/roomtype";
	}

	@RequestMapping("/roomtype/save")
	public String roomtypeSave(Model model, RoomType roomtype) {
		roomTypeDAO.save(roomtype);
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		return "redirect:/admin/roomtype";
	}

	@RequestMapping("/roomtype/edit/{id}")
	public String roomtypeEdit(Model model, @PathVariable("id") String id) {
		RoomType roomtype = roomTypeDAO.findById(id).get();
		model.addAttribute("roomtype", roomtype);
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		return "admin/form/roomtype-form";
	}

	// RoomType Form
	@GetMapping("/roomtype-form")
	public String viewForm(@ModelAttribute("roomtype") RoomType roomType, Model model) {
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		return "admin/form/roomtype-form";
	}

	@ModelAttribute("capacities")
	public Integer[] getCapacities() {
		Integer[] capacities = { 2, 4, 6, 8 };
		return capacities;
	}
}
