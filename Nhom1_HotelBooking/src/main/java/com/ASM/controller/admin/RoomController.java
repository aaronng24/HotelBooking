package com.ASM.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.ASM.dao.HotelDAO;
import com.ASM.dao.RoomDAO;
import com.ASM.dao.RoomTypeDAO;
import com.ASM.model.Hotel;
import com.ASM.model.Room;
import com.ASM.model.RoomType;
import com.ASM.model.User;
import com.ASM.service.SessionService;

@Controller
@RequestMapping("/admin")
public class RoomController {
	@Autowired
	RoomDAO roomDAO;
	@Autowired
	RoomTypeDAO roomTypeDAO;
	@Autowired
	HotelDAO hotelDAO;
	@Autowired
	SessionService sessionService;
	
	@RequestMapping("/room")
	public String room(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		Room room = new Room();
		model.addAttribute("room", room);
		Pageable pageable = PageRequest.of(page, 5);
		Page<Room> Rpage;
		if (keyword.isEmpty()) {
	        Rpage = roomDAO.findAll(pageable);
	    } else {
	        Rpage = roomDAO.findRoomByHotelNameOrTypeName("%" + keyword + "%", pageable);
	    }
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		model.addAttribute("page", Rpage);
		model.addAttribute("rooms", Rpage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", Rpage.getTotalPages());
		model.addAttribute("keyword", keyword);
		return "admin/room";
	}

	// Room Form
	@RequestMapping("/room-form")
	public String viewForm(Model model, @ModelAttribute("room") Room room) {
		List<RoomType> typeList = roomTypeDAO.findAll();
		model.addAttribute("typeList", typeList);
		List<Hotel> hotelList = hotelDAO.findAll();
		model.addAttribute("hotels", hotelList);
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		return "admin/form/room-form";
	}

	@RequestMapping("/room/save")
	public String roomSave(Room room, Model model) {
		roomDAO.save(room);
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		return "redirect:/admin/room";
	}

	@RequestMapping("/room/edit/{id}")
	public String roomEdit(Model model, @PathVariable("id") String id) {
		Room room = roomDAO.findById(id).get();
		List<RoomType> typeList = roomTypeDAO.findAll();
		List<Hotel> hotelList = hotelDAO.findAll();
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		model.addAttribute("hotels", hotelList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("room", room);
		return "admin/form/room-form";
	}

	@ModelAttribute("room-status")
	public Map<String, String> getRoomStatus() {
		Map<String, String> map = new HashMap<>();
		map.put("A", "Active");
		map.put("B", "Booked");
		map.put("I", "Inactive");
		return map;
	}
}
