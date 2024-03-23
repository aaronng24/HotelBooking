package com.ASM.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Hotels")
public class Hotel implements Serializable{
	@Id
	@Size(min = 1 , message="{size.hotel.HotelID}")
	String hotelid;
	@Column(name="hotelname",columnDefinition = "nvarchar(250)",nullable = false)
	@Size(min = 1 , message="{size.hotel.hotelName}")
	String hotelName;
	@Size(min = 10, max=10, message="{size.hotel.phoneNum}")
	String phoneNum;
	@NotBlank(message="{NotBlank.hotel.email}")
	@Email(message="{Email.hotel.email}")
	String email;
	@Column(name="address",columnDefinition = "nvarchar(250)",nullable = false)
	@Size(min = 5, message="{size.hotel.address}")
	String address;
	@Column(name="city",columnDefinition = "nvarchar(250)",nullable = false)
	String city;
	int stars = 1;
	@Column(name="status",columnDefinition = "nvarchar(250)",nullable = false)
	String status = "Active";
	String image;
	@Column(name="description",columnDefinition = "nvarchar(250)",nullable = true)
	@Size(min = 5, message="{size.hotel.description}")
	String description;
	@OneToMany (mappedBy = "hotel")
	List<Room> rooms;
}
