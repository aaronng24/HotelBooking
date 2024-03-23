package com.ASM.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ASM.model.Booking;
import com.ASM.model.Hotel;

public interface BookingDAO extends JpaRepository<Booking, String> {
	@Query(value = "select * from booking where email=?1", nativeQuery = true)
	List<Booking> findAllByEmail(String email);
	
	@Query(value = "select * from booking where bookingid = ?1 or email = ?1", nativeQuery = true)
	Page<Booking> findAllByIDorEmail(String keywords, Pageable pageable);
	
	@Query(value="select SUM(total_price) as total from booking GROUP BY MONTH(checkoutdate)",nativeQuery = true)
	List<Double> takePrice();
	
	@Query(value="select SUM(total_price) from booking b,hotels h, rooms r where b.roomid=r.roomid and r.hotelid=h.hotelid and h.hotelid= ?1 GROUP BY MONTH(checkoutdate)",nativeQuery = true)
	List<Double> takePriceByHotel(String hotelID);
	
	@Query(value = "select COUNT(b.roomid) from booking b, hotels h, rooms r WHERE b.roomid=r.roomid and r.hotelid=h.hotelid GROUP BY h.hotelid,h.hotelname", nativeQuery = true)
	List<Integer> getSoLanKSBooked();
	
	@Query(value = "select h.hotelname from booking b, hotels h, rooms r WHERE b.roomid=r.roomid and r.hotelid=h.hotelid GROUP BY h.hotelid,h.hotelname", nativeQuery = true)
	List<String> getTopKSBooked();
}
