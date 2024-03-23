package com.ASM.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Booking")
public class Booking {
	@Id
	String bookingID;
	double totalPrice;
	@Temporal(TemporalType.DATE)
	@Column(name="checkindate")
	LocalDate CheckInDate;
	@Temporal(TemporalType.DATE)
	@Column(name="checkoutdate")
	LocalDate CheckOutDate;
	String status;
	@ManyToOne @JoinColumn(name = "roomID")
	Room room;
	@ManyToOne @JoinColumn(name="email")
	User user;
}
