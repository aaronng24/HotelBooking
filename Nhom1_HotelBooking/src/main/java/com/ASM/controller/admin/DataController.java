package com.ASM.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ASM.dao.BookingDAO;

@RestController
public class DataController {
	@Autowired
	BookingDAO bkDao;

	@GetMapping("/getDataFromBooking")
	public List<Double> getDataDoanhThu(@RequestParam("hotelName") String hotelID) {
		List<Double> listDataDoanhThu = bkDao.takePriceByHotel(hotelID);
		System.out.println(listDataDoanhThu);
		return listDataDoanhThu;
	}

	@GetMapping("/getDataSoLanDat")
	public List<Integer> getDataSoLanBook() {
		List<Integer> listDataSoLan = bkDao.getSoLanKSBooked();
		System.out.println(listDataSoLan);
		return listDataSoLan;
	}
	
	@GetMapping("/getListKS")
	public List<String> getListKS(){
		List<String> listKS = bkDao.getTopKSBooked();
		System.out.println(listKS);
		return listKS;
	}
}
