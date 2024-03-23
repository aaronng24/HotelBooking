
package com.ASM.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;

	/**
	 * Đọc chuỗi giá trị của tham số
	 * 
	 * @param name         tên tham số
	 * @param defaultValue giá trị mặc định
	 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
	 */
	public String getString(String name, String defaultValue) {
		/**
		 * Đọc số nguyên giá trị của tham số
		 * 
		 * @param name         tên tham số
		 * @param defaultValue giá trị mặc định
		 * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
		 */
		String value = request.getParameter(name);
		return value != null ? value : defaultValue;
	}

	public int getInt(String name, int defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		return Integer.parseInt(value);
	}

	public double getDouble(String name, double defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		return Double.parseDouble(value);
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		String value = getString(name, String.valueOf(defaultValue));
		return Boolean.parseBoolean(value);
	}

	public Date getDate(String name, String pattern) throws RuntimeException {
		String value = getString(name, "");
		try {
			return new SimpleDateFormat(pattern).parse(value);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public File save(MultipartFile file, String path) throws RuntimeException {
		if (!file.isEmpty()) {
			File dir = new File(request.getServletContext().getRealPath(path));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			try {
				File savedFile = new File(dir, file.getOriginalFilename());
				file.transferTo(savedFile);
				return savedFile;
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException();
			}
		}
		return null;
	}
}
