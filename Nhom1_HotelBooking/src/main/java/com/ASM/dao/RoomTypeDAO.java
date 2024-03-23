package com.ASM.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ASM.model.RoomType;

public interface RoomTypeDAO extends JpaRepository<RoomType, String>{
	@Query("SELECT o FROM RoomType o WHERE o.typeName LIKE ?1")
	Page<RoomType> findByTypeName(String typeName, Pageable pageable);
	
	@Query(value = "SELECT rt.typeid,rt.capacity,rt.description,rt.image,rt.price,rt.typename FROM room_type rt INNER JOIN rooms r ON rt.typeid = r.typeid INNER JOIN hotels h ON r.hotelid = h.hotelid WHERE h.hotelid = ?1", nativeQuery = true)
	List<RoomType> findRoomTypeByHotel(String hotelid);
}
