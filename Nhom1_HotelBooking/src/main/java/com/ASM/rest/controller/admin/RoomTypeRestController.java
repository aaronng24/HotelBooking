package com.ASM.rest.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ASM.dao.RoomTypeDAO;
import com.ASM.model.RoomType;
import com.ASM.service.RoomTyperRestService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/roomtypes")
public class RoomTypeRestController {
	@Autowired
	RoomTyperRestService roomTyperRestService;
	
	@GetMapping
	public List<RoomType> getAll(){
		return roomTyperRestService.findAll();
	}
	
	@GetMapping("{id}")
	public RoomType getOne(@PathVariable("id")String id){
		return roomTyperRestService.findById(id);
	}
	
}
