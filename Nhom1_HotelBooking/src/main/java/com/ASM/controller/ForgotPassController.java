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
public class ForgotPassController {
	@Autowired
	AccountDAO accDao;
	@Autowired
	UserDAO userDao;

	@Autowired
	EmailService emailService;

	@Autowired
	SessionService session;
	
	private String generateOTP() {
		// Tạo mã OTP 6 chữ số ngẫu nhiên
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}
//  Quên mật khẩu
	@GetMapping("/forgotpass")
	public String showFogotForm(Model model) {
		model.addAttribute("user", new User());
		return "user/forgotpass";
	}

	@PostMapping("/forgotpass")
	public String forgot(User user, Model model, @RequestParam("email") String email) {
		// Kiểm tra email đã tồn tại hay chưa
		if (!userDao.existsByEmail(user.getEmail())) {
			model.addAttribute("error", "Email chưa được đăng ký!");
			return "user/forgotpass";
		}
		/*
		 * if (!password.equals(check_password)) { model.addAttribute("error1",
		 * "Mật khẩu không trùng nhau!"); return "user/forgotpass"; }
		 */
		Account account = accDao.findByEmailJPQL(email);
		session.set("forgot_Acc", account);
		session.set("forgot_email", email);
		/* session.set("newPass",password); */

		// Tạo mã OTP
		String otp = generateOTP();

		// Gửi email với mã OTP
		emailService.sendOTP(email, otp);

		// Lưu mã OTP và thời gian tạo vào session
//      HttpSession session = request.getSession();

		session.set("forgot_otp", otp);
		session.set("forgot_createTime", System.currentTimeMillis());

		return "user/check_forgot_otp";
	}
	
	@PostMapping("/verify-otp-forgot")
	public String verifyOTP(@RequestParam("otp") String otp, User user, Model model) {
//        HttpSession session = request.getSession();
		String savedOTP = (String) session.get("forgot_otp");
		long createTime = (long) session.get("forgot_createTime");

		// Kiểm tra mã OTP
		if (otp.equals(savedOTP) && (System.currentTimeMillis() - createTime) <= 60000) {
			// Lưu mật khẩu
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			// Chỉnh mk acc vào database
			

			// Xóa OTP và thời gian tạo khỏi session
			session.set("forgot_otp", null);
			session.set("forgot_createTime", null);
			return"user/newpass";
		} else {
			model.addAttribute("message", "Mã OTP không hợp lệ hoặc đã hết hạn!");
			return "user/check_forgot_otp";
		}
	}
	@PostMapping("/newpass")
	public String newpass(User user, Model model, @RequestParam("password") String password,
			@RequestParam("check_password") String check_password) {
		if (!password.equals(check_password)) {
			model.addAttribute("error1", "Mật khẩu không trùng nhau!");
			return "user/newpass";
		}else {
			Account newPassAcc = (Account) session.get("forgot_Acc");
			newPassAcc.setPassword(password);
			accDao.save(newPassAcc);
			session.set("forgot_Acc", null);
			session.set("forgot_email", null);
			return "redirect:/login";
		}
	}
	@GetMapping("/resend-otp-forgot")
	public String resendOTP(Model model) {
		// Lấy email từ session
		
		String email = (String) session.get("forgot_email");

		if (email != null) {
			// Tạo mã OTP mới
			String otp = generateOTP();

			// Gửi lại email với mã OTP mới
			emailService.sendOTP(email, otp);

			// Lưu lại mã OTP và thời gian tạo mới vào session
			session.set("forgot_otp", otp);
			session.set("forgot_createTime", System.currentTimeMillis());

			// Thêm thông báo thành công
			model.addAttribute("message", "Mã OTP đã được gửi lại!");
		} else {
			// Thêm thông báo lỗi
			model.addAttribute("message", "Không thể gửi lại OTP. Vui lòng thử lại!");
		}

		return "user/check_forgot_otp";
	}
}
