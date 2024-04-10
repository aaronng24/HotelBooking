package com.ASM.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ASM.dao.BookingDAO;

@RestController
public class DataController {
	@Autowired
	BookingDAO bkDao;
	
	@GetMapping("/getDataFromBooking")
	public List<Double> getListDataByHotel(@RequestParam(required = false,name="hotelName")String hotelID){
		List<Double> listDataByHotel = bkDao.takePriceByHotel(hotelID);
		System.out.println(listDataByHotel);
		return listDataByHotel;
	}
	
	@GetMapping("/getDataSoLanBook")
	public List<Integer> getSoLanBook(){
		List<Integer> listSoLanBook = bkDao.getSoLanKSBooked();
		System.out.println(listSoLanBook);
		return listSoLanBook;
	}
}
