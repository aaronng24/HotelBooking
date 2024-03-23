package com.ASM.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Rooms")
public class Room {
	@Id
	String roomID;
	String status;
	@ManyToOne @JoinColumn(name="HotelID")
	Hotel hotel;
	@ManyToOne @JoinColumn(name="typeid")
	RoomType roomType;
	@OneToMany (mappedBy = "room")
	List<Booking> bookings;
}
