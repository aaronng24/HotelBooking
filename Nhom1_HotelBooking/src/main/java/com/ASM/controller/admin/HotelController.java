package com.ASM.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ASM.dao.HotelDAO;
import com.ASM.model.City;
import com.ASM.model.Hotel;
import com.ASM.model.User;
import com.ASM.service.SessionService;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class HotelController {
	@Autowired
	HotelDAO hotelDAO;
	@Autowired
	ServletContext app;
	@Autowired
	SessionService session;

	@GetMapping("/hotel")
	public String hotel(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		// Phân trang với 5 hotel trên mỗi trang
		Pageable pageable = PageRequest.of(page, 5);
		Page<Hotel> hotelPage;

		if (keyword.isEmpty()) {
			// Nếu không có từ khóa tìm kiếm, hiển thị tất cả khách sạn
			hotelPage = hotelDAO.findAll(pageable);
		} else {
			// Nếu có từ khóa tìm kiếm, tìm kiếm theo tên khách sạn
			hotelPage = hotelDAO.findAllByNameLike("%" + keyword + "%", pageable);
		}
		User user = (User) session.get("user");
		model.addAttribute("user", user);
		model.addAttribute("hotels", hotelPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", hotelPage.getTotalPages());
		model.addAttribute("keyword", keyword);
		return "admin/hotel";
	}

	@GetMapping("/hotel-form/edit/{hotelid}")
	public String editHotel(Model model, @PathVariable("hotelid") String hotelid) {
		Hotel hotel = hotelDAO.findById(hotelid).get();
		User user = (User) session.get("user");
		model.addAttribute("user", user);
		model.addAttribute("hotel", hotel);
		return "admin/form/hotel-form";
	}

	@GetMapping("/hotel-form")
	public String viewForm(@ModelAttribute("hotel") Hotel hotel, Model model) {
		User user = (User) session.get("user");
		model.addAttribute("user", user);
		return "admin/form/hotel-form";
	}

	@PostMapping("/hotel-form/save")
	public String create(@Valid @ModelAttribute("hotel") Hotel hotel, BindingResult result,
			@RequestParam("attach") MultipartFile attach, Model model) throws IllegalStateException, IOException {
		if (!attach.isEmpty()) {
			String fileName = attach.getOriginalFilename();
			File file = new File(app.getRealPath("/views/img/") + fileName);
			System.out.println(app.getRealPath("/views/img/"));
			attach.transferTo(file);
			hotel.setImage(fileName);
		} else {
			System.out.println("The file is empty!");
		}

		if (result.hasErrors()) {
			model.addAttribute("message", "Vui lòng sửa các lỗi sau:");
			return "/admin/form/hotel-form";
		} else {
			hotelDAO.save(hotel);
			model.addAttribute("message", "Chúc mừng, bạn đã nhập đúng");
			return "/admin/form/hotel-form";

		}

	}

	@ModelAttribute("city")
	public List<City> getCities() {
		List<City> list = new ArrayList<>();
		list.add(new City("An Giang", "An Giang"));
		list.add(new City("Vũng Tàu", "Vũng Tàu"));
		list.add(new City("Bạc Liêu", "Bạc Liêu"));
		list.add(new City("Bắc Kạn", "Bắc Kạn"));
		list.add(new City("Bắc Giang", "Bắc Giang"));
		list.add(new City("Bắc Ninh", "Bắc Ninh"));
		list.add(new City("Bến Tre", "Bến Tre"));
		list.add(new City("Bình Dương", "Bình Dương"));
		list.add(new City("Bình Định", "Bình Định"));
		list.add(new City("Bình Phước", "Bình Phước"));
		list.add(new City("Bình Thuận", "Bình Thuận"));
		list.add(new City("Cà Mau", "Cà Mau"));
		list.add(new City("Cao Bằng", "Cao Bằng"));
		list.add(new City("Đắk Lắk", "Đắk Lắk"));
		list.add(new City("Đắk Nông", "Đắk Nông"));
		list.add(new City("Điện Biên", "Điện Biên"));
		list.add(new City("Đồng Nai", "Đồng Nai"));
		list.add(new City("Đồng Tháp", "Đồng Tháp"));
		list.add(new City("Gia Lai", "Gia Lai"));
		list.add(new City("Hà Giang", "Hà Giang"));
		list.add(new City("Hà Nam", "Hà Nam"));
		list.add(new City("Hà Tĩnh", "Hà Tĩnh"));
		list.add(new City("Hải Dương", "Hải Dương"));
		list.add(new City("Hậu Giang", "Hậu Giang"));
		list.add(new City("Hòa Bình", "Hòa Bình"));
		list.add(new City("Hưng Yên", "Hưng Yên"));
		list.add(new City("Khánh Hòa", "Khánh Hòa"));
		list.add(new City("Kiên Giang", "Kiên Giang"));
		list.add(new City("Kon Tum", "Kon Tum"));
		list.add(new City("Lai Châu", "Lai Châu"));
		list.add(new City("Đà Lạt", "Đà Lạt"));
		list.add(new City("Lạng Sơn", "Lạng Sơn"));
		list.add(new City("Lào Cai", "Lào Cai"));
		list.add(new City("Long An", "Long An"));
		list.add(new City("Nam Định", "Nam Định"));
		list.add(new City("Nghệ An", "Nghệ An"));
		list.add(new City("Ninh Bình", "Ninh Bình"));
		list.add(new City("Ninh Thuận", "Ninh Thuận"));
		list.add(new City("Phú Thọ", "Phú Thọ"));
		list.add(new City("Phú Yên", "Phú Yên"));
		list.add(new City("Quảng Bình", "Quảng Bình"));
		list.add(new City("Quảng Nam", "Quảng Nam"));
		list.add(new City("Quảng Ngãi", "Quảng Ngãi"));
		list.add(new City("Quảng Ninh", "Quảng Ninh"));
		list.add(new City("Quảng Trị", "Quảng Trị"));
		list.add(new City("Sóc Trăng", "Sóc Trăng"));
		list.add(new City("Sơn La", "Sơn La"));
		list.add(new City("Tây Ninh", "Tây Ninh"));
		list.add(new City("Thái Bình", "Thái Bình"));
		list.add(new City("Thái Nguyên", "Thái Nguyên"));
		list.add(new City("Thanh Hóa", "Thanh Hóa"));
		list.add(new City("Huế", "Huế"));
		list.add(new City("Tiền Giang", "Tiền Giang"));
		list.add(new City("Trà Vinh", "Trà Vinh"));
		list.add(new City("Tuyên Quang", "Tuyên Quang"));
		list.add(new City("Vĩnh Long", "Vĩnh Long"));
		list.add(new City("Vĩnh Phúc", "Vĩnh Phúc"));
		list.add(new City("Yên Bái", "Yên Bái"));
		list.add(new City("Cần Thơ", "Cần Thơ"));
		list.add(new City("Đà Nẵng", "Đà Nẵng"));
		list.add(new City("Hải Phòng", "Hải Phòng"));
		list.add(new City("Hà Nội", "Hà Nội"));
		list.add(new City("Phú Quốc", "Phú Quốc"));
		list.add(new City("Nha Trang", "Nha Trang"));
		list.add(new City("Phan Thiết", "Phan Thiết"));
		list.add(new City("Hạ Long", "Hạ Long"));
		list.add(new City("TP. Hồ Chí Minh", "TP. Hồ Chí Minh"));
		return list;
	}

	@ModelAttribute("stars")
	public Integer[] getStars() {
		Integer[] stars = { 1, 2, 3, 4, 5 };
		return stars;
	}

	@ModelAttribute("status")
	public Map<String, String> getHotelStatus() {
		Map<String, String> map = new HashMap<>();
		map.put("Active", "Active");
		map.put("Inactive", "Inactive");
		return map;
	}
}
