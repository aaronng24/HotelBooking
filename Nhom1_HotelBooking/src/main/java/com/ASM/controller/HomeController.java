package com.ASM.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.HotelDAO;
import com.ASM.model.Account;
import com.ASM.model.Hotel;
import com.ASM.model.HotelWithPrice;
import com.ASM.model.User;
import com.ASM.service.SessionService;
import com.ASM.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	@Autowired
	SessionService session;
	@Autowired
	HotelDAO hotelDAO;
	
	
	@GetMapping("/home")
	public String getHomePage(Model model) {
		User userLogin = (User)session.get("user");
		Account account = (Account) session.get("useracc");
		
		List<HotelWithPrice> listWPrice = new ArrayList<>();

		for (Hotel hotel : hotelDAO.top8List()) {
			double price = Double.parseDouble(String.valueOf(hotelDAO.findMinPriceByHotel(hotel.getHotelid())));
			listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(), hotel.getAddress(),
					hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
		}
		model.addAttribute("hotels",listWPrice);
		model.addAttribute("user",userLogin);
		model.addAttribute("useracc", account);
		model.addAttribute("view","home.jsp");
		return "layout/index";
	}
	
	
}
