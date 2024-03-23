package com.ASM.service;

import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;

@Service
public class EmailService {
	@Autowired
	static JavaMailSender emailSender;

	public EmailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendOTP(String email, String otp) {
		MimeMessage message = emailSender.createMimeMessage();

		try {
			message.setFrom("dhuyclone2001@gmail.com");
			message.addRecipient(RecipientType.TO, new InternetAddress(email));
			message.setSubject("Mã OTP xác nhận đăng ký");
			message.setText("Mã OTP của bạn là: " + otp);
			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendEmail(String to, String bookingID, String cusName, String hotelName, String roomType,
			LocalDate bookingFromDates, LocalDate bookingToDates, double totalPrice) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String formattedTotalPrice = currencyFormatter.format(totalPrice);
		String body = "<html><body><h3>Thông tin đặt phòng</h3>" + "<b>Mã đặt phòng: </b>" + bookingID + "<br>\r\n"
				+ "<b>Khách hàng: </b>" + cusName + "<br>\r\n" + "<b>Tên khách sạn: </b>" + hotelName + "<br>\r\n"
				+ "<b>Loại phòng: </b>" + roomType + "<br>\r\n" + "<b>Ngày đặt: </b>" + bookingFromDates + " <b>đến</b> "
				+ bookingToDates + "<br>\r\n" + "<b>Tổng tiền: </b>" + formattedTotalPrice + "<br><br>\r\n"
				+ "Trân trọng,<br><br>"
				+ "--------------------------------------------------" + "</body></html>";
		helper.setFrom("dhuyclone2001@gmail.com");
		helper.setTo(to);
		helper.setSubject("Xác nhận đặt phòng");
		helper.setText(body, true);
		emailSender.send(message);
	}

}
