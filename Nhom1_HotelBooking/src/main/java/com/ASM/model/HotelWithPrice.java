package com.ASM.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelWithPrice {
	String id;
	String name;
	String address;
	int stars;
	Double price;
	String image;
	String city;
}