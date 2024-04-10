package com.ASM.service;

import java.util.List;
import java.util.Optional;

import com.ASM.model.Room;

public interface RoomRestService {
	List<Room> findAll();

	Room findByID(String id);

	Room create(Room room);

	void delete(String id);

	List<Room> findAllByName(Optional<String> name);
}
