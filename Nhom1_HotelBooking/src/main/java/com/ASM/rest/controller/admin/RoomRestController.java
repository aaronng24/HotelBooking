package com.ASM.rest.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ASM.model.Room;
import com.ASM.service.RoomRestService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/room")
public class RoomRestController {
	@Autowired
	RoomRestService roomRestService;

	@GetMapping
	public List<Room> getAll() {
		return roomRestService.findAll();
	}
	
	@PostMapping
	public Room create(@RequestBody Room room) {
		return roomRestService.create(room);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {
		roomRestService.delete(id);
	}
	
	@GetMapping("{name}")
	public List<Room> getByName(@PathVariable("name") Optional<String> name){
		if(name.isEmpty()) {
			return roomRestService.findAll();
		}
		return roomRestService.findAllByName(name);
	}
}
