package com.ASM.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="RoomType")
public class RoomType {
	@Id
	String typeid;
	@Column(name="typename",columnDefinition = "nvarchar(50)",nullable = false)
	String typeName;
	double price;
	int capacity;
	String image;
	@Column(name="description",columnDefinition = "nvarchar(250)",nullable = true)
	String description;
	@OneToMany (mappedBy = "roomType")
	List<Room> rooms;
	

}
