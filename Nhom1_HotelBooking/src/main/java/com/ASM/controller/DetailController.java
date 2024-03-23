package com.ASM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ASM.dao.HotelDAO;
import com.ASM.dao.RoomDAO;
import com.ASM.dao.RoomTypeDAO;
import com.ASM.model.Account;
import com.ASM.model.Hotel;
import com.ASM.model.Room;
import com.ASM.model.RoomType;
import com.ASM.model.User;
import com.ASM.service.SessionService;

@Controller
public class DetailController {
	@Autowired
	HotelDAO dao;
	@Autowired
	SessionService session;
	@Autowired
	RoomTypeDAO rtDAO;
	@Autowired
	private RoomDAO rDAO;
	
	List<RoomType> list = new ArrayList<>();
	
	
	@RequestMapping("/hotel/detail/{id}")
	public String getDetail(@PathVariable("id")String id,Model model) {
		Hotel hotel = dao.findById(id).get();
		session.set("hotel", hotel);
		list=rtDAO.findRoomTypeByHotel(id);
		
		model.addAttribute("roomType",list);	
		model.addAttribute("hotel",hotel);
		model.addAttribute("view","detail.jsp");
		User userLogin = (User)session.get("user");
		Account account = (Account) session.get("useracc");
		model.addAttribute("user",userLogin);
		model.addAttribute("useracc", account);
		return "layout/index";
	}
	


}