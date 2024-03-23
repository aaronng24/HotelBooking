package com.ASM.controller;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.AccountDAO;
import com.ASM.dao.BookingDAO;
import com.ASM.dao.HotelDAO;
import com.ASM.dao.UserDAO;
import com.ASM.dao.WalletDAO;
import com.ASM.model.Account;
import com.ASM.model.Booking;
import com.ASM.model.Hotel;
import com.ASM.model.Session;
import com.ASM.model.User;
import com.ASM.model.Wallet;
import com.ASM.service.SessionService;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	UserDAO uDAO;
	@Autowired
	HotelDAO hotelDAO;
	@Autowired
	SessionService sessionService;
	@Autowired
	WalletDAO wallDao;
	@Autowired
	BookingDAO bookDao;
	@Autowired
	AccountDAO accDao;

	@RequestMapping("/wallet")
	public String myAccount(Model model) {
		User user = (User) sessionService.get("user");
		Account account = (Account) sessionService.get("useracc");
		model.addAttribute("useracc", account);
		model.addAttribute("user", user);
		Wallet wallet = user.getWallet();
		model.addAttribute("wallet", wallet);
		
		model.addAttribute("view", "wallet.jsp");
		return "user/my-profile";
	}

	@RequestMapping("/wallet/add")
	public String credit( Model model, @RequestParam("credit")Optional<Double> credit) {
		Double Credit = credit.orElse(0.0);
		Account account = (Account) sessionService.get("useracc");
		User user = (User) sessionService.get("user");
		Wallet wallet = user.getWallet();
		wallet.setCredit(wallet.getCredit()+ Credit);
		wallDao.save(wallet);
		model.addAttribute("wallet",wallet);
		model.addAttribute("useracc", account);
		model.addAttribute("tenten", "Nạp tiền thành công");
		model.addAttribute("view", "wallet.jsp");
		return "user/my-profile";
	}

	@RequestMapping("/booking")
	public String myBooking(Model model) {
		User user = (User) sessionService.get("user");
		Account account = (Account) sessionService.get("useracc");
		model.addAttribute("useracc", account);
		model.addAttribute("user", user);
		model.addAttribute("view", "booking.jsp");
		return "user/my-profile";
	}

	@RequestMapping("/history")
	public String myHistory(Model model) {
		User user = (User) sessionService.get("user");
		model.addAttribute("user", user);
		String email = user.getEmail();
		List<Booking> bkk = bookDao.findAllByEmail(email);
		Account account = (Account) sessionService.get("useracc");
		model.addAttribute("useracc", account);
		model.addAttribute("bookings", bkk);
		model.addAttribute("view", "history.jsp");
		return "user/my-profile";
	}

	@RequestMapping("/edit-profile")
	public String myProfileEdit(Model model) {
		User user = (User) sessionService.get("user");
		Account account = (Account) sessionService.get("useracc");
		model.addAttribute("useracc", account);
		model.addAttribute("user", user);
		model.addAttribute("view", "edit-profile.jsp");
		return "user/my-profile";
	}

	@RequestMapping("/edit-profile/update")
	public String update(User user, Model model) {
		Account account = (Account) sessionService.get("useracc");
		uDAO.save(user);
		model.addAttribute("user",user);
		model.addAttribute("useracc",account);
		model.addAttribute("message", "Cập nhật thành công");
		model.addAttribute("view", "edit-profile.jsp");
		return "user/my-profile";
	}

	@ModelAttribute("gender")
	public Map<String, String> getGender() {
		Map<String, String> map = new HashMap<>();
		map.put("Nam", "Nam");
		map.put("Nữ", "Nữ");
		map.put("Khác", "Giới tính khác");
		return map;
	}

	@RequestMapping("/pass-change")
	public String change(Model model) {
		Account acc = (Account) sessionService.get("useracc");
		User users= (User) sessionService.get("user");
		
		model.addAttribute("user",users);
		model.addAttribute("useracc",acc);
		model.addAttribute("view", "pass-change.jsp");
		return "user/my-profile";
	}
	
	@RequestMapping("/change")
	public String changed(Model model,@RequestParam("password") String password,
			@RequestParam("newpassword") String newpassword, @RequestParam("confirmpassword") String confirmpassword) {
		Account acc = (Account) sessionService.get("useracc");
		User users= (User) sessionService.get("user");
		
		try {
			if (!acc.getPassword().equals(password)) {
				model.addAttribute("mess", "Mật khẩu cũ không đúng!");
			} else {
				if (newpassword.equals(confirmpassword)) {
					acc.setPassword(confirmpassword);
					accDao.save(acc);
					model.addAttribute("mess", "Mật khẩu đã được thay đổi!");
				} else {
					model.addAttribute("mess", "Xác nhận mật khẩu không khớp nhau!");
				}
			}
		} catch (Exception e) {
			model.addAttribute("mess", "Account không đúng!");
		}
		model.addAttribute("user",users);
		model.addAttribute("useracc",acc);
		
		model.addAttribute("view", "pass-change.jsp");
		return "user/my-profile";
	}
	
	@RequestMapping("/logout")
	public String logOut() {
		User userLogin = (User) sessionService.get("user");
		if (userLogin != null)
			sessionService.set("user", null);
			sessionService.set("useracc", null);
		return "redirect:/home";
	}
}
