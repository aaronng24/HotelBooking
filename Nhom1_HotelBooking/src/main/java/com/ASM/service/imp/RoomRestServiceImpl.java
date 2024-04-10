package com.ASM.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.dao.RoomDAO;
import com.ASM.model.Room;
import com.ASM.service.RoomRestService;

@Service
public class RoomRestServiceImpl implements RoomRestService {
	@Autowired
	RoomDAO roomDAO;

	@Override
	public List<Room> findAll() {
		// TODO Auto-generated method stub
		return roomDAO.findAll();
	}

	@Override
	public Room findByID(String id) {
		// TODO Auto-generated method stub
		return roomDAO.findById(id).get();
	}

	@Override
	public Room create(Room room) {
		// TODO Auto-generated method stub
		return roomDAO.save(room);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		roomDAO.deleteById(id);
	}

	@Override
	public List<Room> findAllByName(Optional<String> name) {
		// TODO Auto-generated method stub
		return roomDAO.findRoomByHotelNameOrTypeName(name);
	}

}
