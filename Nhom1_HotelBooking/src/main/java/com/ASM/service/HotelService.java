package com.ASM.service;

import java.util.Collection;
import java.util.List;

import com.ASM.model.Hotel;
import com.ASM.model.HotelWithPrice;

public interface HotelService {
	Hotel add(String id);
	void remove(String id);
	Hotel update(String id,String qty);
	void clear();
	Collection <Hotel> getItems();
	int getCount();
}
