package com.ASM.controller;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
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
import com.ASM.model.Account;
import com.ASM.model.City;
import com.ASM.model.Hotel;
import com.ASM.model.HotelWithPrice;
import com.ASM.model.RoomType;
import com.ASM.model.User;
import com.ASM.service.SessionService;

import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ListHotelsController {
	@Autowired
	HotelDAO hotelDAO;
	@Autowired
	SessionService session;

	@RequestMapping("/hotel/list")
	public String getList(Model model, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max, @RequestParam(required = false, name = "star") int[] star) {

		if (star == null) {
			star = new int[] { 1, 2, 3, 4, 5 };
		}
		double minPrice = min.orElse(Double.MIN_VALUE);
		double maxPrice = max.orElse(Double.MAX_VALUE);

		model.addAttribute("listHotel", listWPrice(minPrice, maxPrice, star));

		Account account = (Account) session.get("useracc");
		User userLogin = (User) session.get("user");
		model.addAttribute("cityList", getCities());
		model.addAttribute("user", userLogin);
		model.addAttribute("useracc", account);
		model.addAttribute("view", "list.jsp");
		return "layout/index";
	}

	private List<HotelWithPrice> listWPrice(double min, double max, int[] star) {
		List<HotelWithPrice> listWPrice = new ArrayList<>();
		// Lấy danh sách hotel có kèm theo giá phòng thấp nhất của hotel
		for (Hotel hotel : hotelDAO.findAll()) {
			double price = Double.parseDouble(String.valueOf(hotelDAO.findMinPriceByHotel(hotel.getHotelid())));
			if (price <= max && price >= min) {
				if (star.length == 0 || star == null) {
					listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(), hotel.getAddress(),
							hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
				} else {
					for (int i = 0; i < star.length; i++) {
						if (star[i] == hotel.getStars()) {
							listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(),
									hotel.getAddress(), hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
						}
					}
				}
			}
		}
		return listWPrice;
	}

//	
	@RequestMapping("/hotel/listTP")
	public String getListVT(@RequestParam("city") String city, Model model, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max, @RequestParam(required = false, name = "star") int[] star) {

		if (star == null) {
			star = new int[] { 1, 2, 3, 4, 5 };
		}
		double minPrice = min.orElse(Double.MIN_VALUE);
		double maxPrice = max.orElse(Double.MAX_VALUE);
		if (city.equals("VungTau")) {
			city = "Vũng Tàu";
		} else if (city.equals("DaNang")) {
			city = "Đà Nẵng";
		} else if (city.equals("HaNoi")) {
			city = "Hà Nội";
		} else if (city.equals("DaLat")) {
			city = "Đà Lạt";
		} else if (city.equals("HCM")) {
			city = "TP. Hồ Chí Minh";
		}
		model.addAttribute("listHotel", listWPriceTP(minPrice, maxPrice, star, city));
		Account account = (Account) session.get("useracc");
		User userLogin = (User) session.get("user");
		model.addAttribute("cityList", getCities());
		model.addAttribute("user", userLogin);
		model.addAttribute("useracc", account);
		model.addAttribute("view", "list.jsp");
		return "layout/index";
	}

	@RequestMapping("/hotel/search")
	public String getSearchList(@RequestParam("city") String city,@RequestParam("capacity") Optional<Integer> capacity, Model model, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max, @RequestParam(required = false, name = "star") int[] star) {
		if (star == null) {
			star = new int[] { 1, 2, 3, 4, 5 };
		}
		double minPrice = min.orElse(Double.MIN_VALUE);
		double maxPrice = max.orElse(Double.MAX_VALUE);
		int newCapa= capacity.orElse(2);
		model.addAttribute("listHotel", listWPriceSearch(city, newCapa, minPrice, maxPrice, star));
		Account account = (Account) session.get("useracc");
		User userLogin = (User) session.get("user");
		model.addAttribute("cityList", getCities());
		model.addAttribute("user", userLogin);
		model.addAttribute("useracc", account);
		model.addAttribute("view", "list.jsp");
		return "layout/index";
	}

	private List<HotelWithPrice> listWPriceTP(double min, double max, int[] star, String city) {
		List<HotelWithPrice> listWPrice = new ArrayList<>();
		// Lấy danh sách hotel có kèm theo giá phòng thấp nhất của hotel
		for (Hotel hotel : hotelDAO.findByCity(city)) {
			double price = Double.parseDouble(String.valueOf(hotelDAO.findMinPriceByHotel(hotel.getHotelid())));
			if (price <= max && price >= min) {
				if (star.length == 0 || star == null) {
					listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(), hotel.getAddress(),
							hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
				} else {
					for (int i = 0; i < star.length; i++) {
						if (star[i] == hotel.getStars()) {
							listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(),
									hotel.getAddress(), hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
						}
					}
				}
			}
		}
		return listWPrice;
	}

	private List<HotelWithPrice> listWPriceSearch(String city, int capacity, double min, double max, int[] star) {
		List<HotelWithPrice> listWPrice = new ArrayList<>();
		// Lấy danh sách hotel có kèm theo giá phòng thấp nhất của hotel
		for (Hotel hotel : hotelDAO.findByCityOrCapacity(city, capacity)) {
			double price = Double.parseDouble(String.valueOf(hotelDAO.findMinPriceByHotel(hotel.getHotelid())));
			if (price <= max && price >= min) {
				if (star.length == 0 || star == null) {
					listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(), hotel.getAddress(),
							hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
				} else {
					for (int i = 0; i < star.length; i++) {
						if (star[i] == hotel.getStars()) {
							listWPrice.add(new HotelWithPrice(hotel.getHotelid(), hotel.getHotelName(),
									hotel.getAddress(), hotel.getStars(), price, hotel.getImage(), hotel.getCity()));
						}
					}
				}
			}
		}
		return listWPrice;
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
}