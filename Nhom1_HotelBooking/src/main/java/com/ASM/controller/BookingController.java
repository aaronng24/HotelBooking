package com.ASM.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ASM.dao.BookingDAO;
import com.ASM.dao.RoomDAO;
import com.ASM.dao.RoomTypeDAO;
import com.ASM.dao.WalletDAO;
import com.ASM.model.Account;
import com.ASM.model.Booking;
import com.ASM.model.Hotel;
import com.ASM.model.Room;
import com.ASM.model.RoomType;
import com.ASM.model.User;
import com.ASM.model.Wallet;
import com.ASM.service.EmailService;
import com.ASM.service.SessionService;
import com.ASM.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BookingController {

	@Autowired
	SessionService session;
	@Autowired
	RoomTypeDAO rtDAO;
	@Autowired
	BookingDAO bkDAO;
	@Autowired
	RoomDAO rDAO;
	@Autowired
	WalletDAO wDAO;
	@Autowired
	VNPayService vnPayService;
	@Autowired
	EmailService emailService;

	@RequestMapping("/booking/{id}")
	public String booking(Model model, @PathVariable("id") String id) {
		Hotel hotel = (Hotel) session.get("hotel");
		User userLogin = (User) session.get("user");
		Account acc = (Account) session.get("useracc");
		RoomType rt = rtDAO.findById(id).get();
		session.set("roomtype", rt);
		if (hotel == null) {
			return "redirect:/hotel/list";
		}
		Room room = rDAO.getAvailableRoom(hotel.getHotelid(), rt.getTypeid());
		if (room == null) {
			return "redirect:/hotel/detail/" + hotel.getHotelid();
		}
		if (userLogin == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("user", userLogin);
		}
		model.addAttribute("useracc", acc);
		model.addAttribute("roomtype", rt);
		model.addAttribute("hotel", hotel);
		model.addAttribute("view", "booking.jsp");
		return "layout/index";
	}

	@RequestMapping("/thanhtoan")
	public String thanhtoan(Model model, @RequestParam("fromDate") Optional<String> fromDate,
			@RequestParam("toDate") Optional<String> toDate) throws ParseException {
		RoomType rt = (RoomType) session.get("roomtype");
		Hotel hotel = (Hotel) session.get("hotel");
		User userLogin = (User) session.get("user");

		if (userLogin == null) {
			return "redirect:/login";
		} else {
			Wallet wallet = userLogin.getWallet();
			model.addAttribute("wallet", wallet);
			model.addAttribute("user", userLogin);
		}
		String from = fromDate.orElse(null);
		String to = toDate.orElse(null);
		LocalDate fromdate = LocalDate.parse(from);
		LocalDate todate = LocalDate.parse(to);
		long countDate = ChronoUnit.DAYS.between(fromdate, todate);
		model.addAttribute("user", userLogin);
		Room room = rDAO.getAvailableRoom(hotel.getHotelid(), rt.getTypeid());

		String bkID = "BKN" + String.valueOf(bkDAO.count() + 1);
		Double beforePrice = rt.getPrice() * countDate;
		Double taxes = beforePrice * 5 / 100;
		double price = (rt.getPrice() * countDate) + (rt.getPrice() * countDate) * 5 / 100;
		Booking bk = new Booking(bkID, price, fromdate, todate, "Done", room, userLogin);

		session.set("tongtien", (int) price);
		// System.out.println(price);
		session.set("booking", bk);
		model.addAttribute("bk", bk);
		model.addAttribute("countDate", countDate);
		model.addAttribute("taxes", taxes);
		model.addAttribute("beforePrice", beforePrice);
		model.addAttribute("totalPrice", price);
		model.addAttribute("bkID", bkID);
		model.addAttribute("user", userLogin);
		model.addAttribute("hotel", hotel);
		model.addAttribute("roomtype", rt);

		return "layout/thanhtoan";
	}

	@RequestMapping("/vnpay")
	public String vnPay(Model model) {
		// System.out.println(session.get("tongtien"));
		model.addAttribute("value", session.get("tongtien"));
		return "user/index";
	}

	@PostMapping("/submitOrder")
	public String submidOrder(@RequestParam("amount") int orderTotal, @RequestParam("orderInfo") String orderInfo,
			HttpServletRequest request) {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
		return "redirect:" + vnpayUrl;
	}

	@GetMapping("/vnpay-payment")
	public String GetMapping(HttpServletRequest request, Model model) throws Exception {
		int paymentStatus = vnPayService.orderReturn(request);

		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");
		User userLogin = (User) session.get("user");
		model.addAttribute("amount", session.get("tongtien"));
		model.addAttribute("orderId", orderInfo);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("paymentTime", paymentTime);
		model.addAttribute("transactionId", transactionId);
		Booking booking = (Booking) session.get("booking");
		Hotel hotel = (Hotel) session.get("hotel");
		RoomType rt = (RoomType) session.get("roomtype");
		if (paymentStatus == 1) {
			bkDAO.save(booking);
			EmailService.sendEmail(userLogin.getEmail(), booking.getBookingID(),
					booking.getUser().getLastName() + " " + booking.getUser().getFirstName(), hotel.getHotelName(),
					rt.getTypeName(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getTotalPrice());
		}

		return paymentStatus == 1 ? "user/ordersuccess" : "user/orderfail";
	}
}
