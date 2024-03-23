package com.ASM.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ASM.model.Hotel;
import com.ASM.model.HotelWithPrice;

public interface HotelDAO extends JpaRepository<Hotel, String> {
	@Query("SELECT o FROM Hotel o WHERE o.hotelName LIKE ?1 OR o.city LIKE ?1")
	Page<Hotel> findAllByNameLike(String keywords, Pageable pageable);

	@Query(value = "SELECT MIN(price) FROM room_type rt INNER JOIN rooms r ON rt.typeid = r.typeid INNER JOIN hotels h ON r.hotelid = h.hotelid WHERE h.hotelid = ?1", nativeQuery = true)
	Double findMinPriceByHotel(String hotelid);

	@Query(value = "select top 8 * from hotels WHERE stars >= 4", nativeQuery = true)
	List<Hotel> top8List();

	@Query("SELECT h FROM Hotel h WHERE h.city = ?1")
	List<Hotel> findByCity(String city);

	@Query(value = "select h.* from hotels h INNER JOIN rooms r ON h.hotelid = r.hotelid INNER JOIN room_type rt ON r.typeid=rt.typeid WHERE h.city = ?1 and rt.capacity = ?2 GROUP by h.hotelid, h.address,h.city,h.[description],h.email, h.hotelname,h.[image],h.phone_num,h.stars,h.[status]", nativeQuery = true)
	List<Hotel> findByCityOrCapacity(String city, int capacity);
	
	@Query("select o from Hotel o where o.hotelid = ?1")
	Hotel findByHotelid(String ID);
	
}
