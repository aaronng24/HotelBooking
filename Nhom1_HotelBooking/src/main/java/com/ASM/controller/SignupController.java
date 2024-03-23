package com.ASM.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ASM.dao.AccountDAO;
import com.ASM.dao.UserDAO;
import com.ASM.model.Account;
import com.ASM.model.User;
import com.ASM.service.EmailService;
import com.ASM.service.SessionService;

@Controller
public class SignupController {
	@Autowired
	AccountDAO accDao;
	@Autowired
	UserDAO userDao;

	@Autowired
	EmailService emailService;

	@Autowired
	SessionService session;
	
//	Đăng kí
	@GetMapping("/signup")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "user/signUp";
	}

	@PostMapping("/signup")
	public String register(User user, Model model, @RequestParam("password") String password,
			@RequestParam("email") String email) {
		// Kiểm tra email đã tồn tại hay chưa
		if (userDao.existsByEmail(user.getEmail())) {
			model.addAttribute("error", "Email đã được sử dụng!");
			return "user/signUp";
		}
		Account account = new Account(password, false, user);
		session.set("newAcc", account);
		session.set("newUser", user);

		// Tạo mã OTP
		String otp = generateOTP();

		// Gửi email với mã OTP
		emailService.sendOTP(user.getEmail(), otp);

		// Lưu mã OTP và thời gian tạo vào session
//        HttpSession session = request.getSession();

		session.set("otp", otp);
		session.set("createTime", System.currentTimeMillis());

		return "user/verify-otp";
	}

	@PostMapping("/verify-otp")
	public String verifyOTP(@RequestParam("otp") String otp, User user, Model model) {
//        HttpSession session = request.getSession();
		String savedOTP = (String) session.get("otp");
		long createTime = (long) session.get("createTime");

		// Kiểm tra mã OTP
		if (otp.equals(savedOTP) && (System.currentTimeMillis() - createTime) <= 60000) {
			// Lưu mật khẩu
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
			// Lưu user vào database
			userDao.save((User) session.get("newUser"));
			// Lưu acc vào database
			accDao.save((Account) session.get("newAcc"));

			// Xóa OTP và thời gian tạo khỏi session
			session.set("otp", null);
			session.set("createTime", null);
			session.set("newAcc", null);
			session.set("newUser", null);
			return "redirect:/login";
		} else {
			model.addAttribute("message", "Mã OTP không hợp lệ hoặc đã hết hạn!");
			return "user/verify-otp";
		}
	}

	private String generateOTP() {
		// Tạo mã OTP 6 chữ số ngẫu nhiên
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}

	@GetMapping("/resend-otp")
	public String resendOTP(Model model) {
		// Lấy email từ session
		User user = (User) session.get("newUser");
		String email = user.getEmail();

		if (email != null) {
			// Tạo mã OTP mới
			String otp = generateOTP();

			// Gửi lại email với mã OTP mới
			emailService.sendOTP(email, otp);

			// Lưu lại mã OTP và thời gian tạo mới vào session
			session.set("otp", otp);
			session.set("createTime", System.currentTimeMillis());

			// Thêm thông báo thành công
			model.addAttribute("message", "Mã OTP đã được gửi lại!");
		} else {
			// Thêm thông báo lỗi
			model.addAttribute("message", "Không thể gửi lại OTP. Vui lòng thử lại!");
		}

		return "user/verify-otp";
	}
}
