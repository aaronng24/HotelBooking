package com.ASM.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ASM.dao.BookingDAO;
import com.ASM.dao.HotelDAO;
import com.ASM.dao.RoomDAO;
import com.ASM.dao.RoomTypeDAO;
import com.ASM.model.Account;
import com.ASM.model.ChartData.DataPointModel;
import com.ASM.model.ChartData.DatabaseConnectionException;
import com.ASM.model.City;
import com.ASM.model.Hotel;
import com.ASM.model.Room;
import com.ASM.model.RoomType;
import com.ASM.model.User;
import com.ASM.service.ChartService;
import com.ASM.service.SessionService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	RoomTypeDAO roomTypeDAO;
	@Autowired
	HotelDAO hotelDAO;
	@Autowired
	RoomDAO roomDAO;
	@Autowired
	SessionService sessionService;
	@Autowired
	ChartService chartService;
	@Autowired
	BookingDAO bkDAO;
	
	// View page
	@RequestMapping("/index")
	public String index(Model model,@RequestParam(required = false,name="hotelName")String hotelID) {
		User user = (User) sessionService.get("user");
		List<Hotel> listHotel= hotelDAO.findAll();
		List<Double> listdata= bkDAO.takePrice();
		//List<Double> dataByHotelID= bkDAO.takePriceByHotel(hotelID);
		model.addAttribute("choosedHotel", hotelDAO.findByHotelid(hotelID));
		//model.addAttribute("listDataByHotel",dataByHotelID);
		model.addAttribute("listData",listdata);
		model.addAttribute("listHotel",listHotel);
		model.addAttribute("user", user);
		return "admin/index";
	}
 
	@ExceptionHandler({DatabaseConnectionException.class})
	public ModelAndView getSuperheroesUnavailable(DatabaseConnectionException ex) {
	    return new ModelAndView("admin/index", "error", ex.getMessage());
	}
	
	@GetMapping("/logout")
	public String accountLogout() {
		User userLogin = (User) sessionService.get("user");
		if (userLogin != null)
			sessionService.set("user", null);
			sessionService.set("useracc", null);
		return "redirect:/home";
	}
	
	@RequestMapping("/thongke-theoKS")
	public String thongKeDoanhThuTheoKS(Model model,@RequestParam(required = false,name="hotelName")String hotelID) {
		User user = (User) sessionService.get("user");
		Hotel choosedHotel = hotelDAO.findByHotelid(hotelID);
		List<Hotel> listHotel= hotelDAO.findAll();
		List<Double> dataByHotelID= bkDAO.takePriceByHotel(hotelID);
		model.addAttribute("choosedHotel", choosedHotel);
		model.addAttribute("listHotel",listHotel);
		model.addAttribute("listDataByHotel",dataByHotelID);
		model.addAttribute("user", user);
		return "admin/thongke-theoKS";
	}
	
	@RequestMapping("/thongke-topKS")
	public String thongkeTopKS(Model model) {
		User user = (User) sessionService.get("user");
		List<Integer> solandat = bkDAO.getSoLanKSBooked();
		List<String> hotelNameBooked = bkDAO.getTopKSBooked();
		List<String> list = new ArrayList<String>();
		for(int i =0; i<hotelNameBooked.size();i++) {
			String value = "'" + hotelNameBooked.get(i) + "'";
			list.add(value);
		}
		model.addAttribute("listHotelName", list);
		model.addAttribute("listDataSoLan", solandat);
		model.addAttribute("user", user);
		return "admin/thongke-topKSbooked";
	}
}
