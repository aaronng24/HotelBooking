package com.ASM.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.dao.RoomTypeDAO;
import com.ASM.model.RoomType;
import com.ASM.service.RoomTyperRestService;

@Service
public class RoomTypeRestServiceImpl implements RoomTyperRestService {
	@Autowired
	RoomTypeDAO rdao;

	@Override
	public List<RoomType> findAll() {
		return rdao.findAll();
	}

	@Override
	public RoomType findById(String id) {
		return rdao.findById(id).get();
	}

	@Override
	public RoomType create(RoomType roomtype) {
		return rdao.save(roomtype);
	}

	@Override
	public void delete(String id) {
		rdao.deleteById(id);
		
	}

	@Override
	public List<RoomType> findAllByTypeName(Optional<String> name) {
		return rdao.findAllByTypeName(name);
	}
}
