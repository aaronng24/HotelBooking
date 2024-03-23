package com.ASM.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "Users")
public class User {
	@Id
	String email;
	@Column(name="firstname",columnDefinition = "nvarchar(50)",nullable = false)
	String firstName;
	@Column(name="lastname",columnDefinition = "nvarchar(50)",nullable = false)
	String lastName;
	String phoneNum;
	@Column(name="gender",columnDefinition = "nvarchar(10)",nullable = false)
	String gender;
	@Temporal(TemporalType.DATE)
	@Column(name="birthdate")
	LocalDate birthdate;
	String status = "Active";
	@OneToOne (mappedBy = "user")
	Wallet wallet;
	@OneToOne(mappedBy = "user")
	Account account;
	@OneToMany (mappedBy = "user")
	List<Booking> bookings;
	
}
