package com.ASM.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import com.ASM.model.Room;

public interface RoomDAO extends JpaRepository<Room, String>{
	@Query("SELECT o FROM Room o WHERE o.hotel.hotelName LIKE ?1 OR o.roomType.typeName LIKE ?1")
	Page<Room> findRoomByHotelNameOrTypeName(String keyword, Pageable pageable);

	@Query(value="  select top 1( roomid),r.status,r.hotelid,typeid from rooms r, hotels h where r.hotelid=h.hotelid and r.status='Available' and r.hotelid= ?1 and r.typeid=?2",nativeQuery = true)
	Room getAvailableRoom(String hotelid, String typeid);

	@Query("select o from Room o where o.roomType.typeid = ?1")
	List<Room> getRoomByRoomType(String id);
	
	@Query(value=" select count(roomid) from rooms where status = 'Available' and typeid=?1 and hotelid=?2",nativeQuery = true)
	Long findTypeID(String typeID,String hotelID);
}
