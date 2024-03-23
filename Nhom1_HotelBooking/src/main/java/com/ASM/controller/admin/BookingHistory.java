package com.ASM.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.BookingDAO;
import com.ASM.model.Booking;
import com.ASM.model.User;
import com.ASM.service.SessionService;

@Controller
@RequestMapping("/admin")
public class BookingHistory {
	@Autowired
	SessionService session;
	@Autowired
	BookingDAO bookingDAO;

	@GetMapping("/booking-history")
	public String getBookingHistory(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		// Phân trang với 5 hotel trên mỗi trang
		Pageable pageable = PageRequest.of(page, 5);
		Page<Booking> bookingHisPage;
		if (keyword.isEmpty()) {
			bookingHisPage = bookingDAO.findAll(pageable);
		} else {
			bookingHisPage = bookingDAO.findAllByIDorEmail("%" + keyword + "%", pageable);
		}
		
		User user = (User) session.get("user");
		model.addAttribute("user", user);
		model.addAttribute("bookings", bookingHisPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bookingHisPage.getTotalPages());
		model.addAttribute("keyword", keyword);
		return "admin/booking-history";
	}
}
