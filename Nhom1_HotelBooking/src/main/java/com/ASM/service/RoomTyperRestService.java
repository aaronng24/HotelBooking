package com.ASM.service;

import java.util.List;

import com.ASM.model.RoomType;

public interface RoomTyperRestService {

	List<RoomType> findAll();

	RoomType findById(String id);

}
