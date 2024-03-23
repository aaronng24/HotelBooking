package com.ASM.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ASM.model.Account;

public interface AccountDAO extends JpaRepository<Account, Integer>{
	@Query("SELECT acc FROM Account acc WHERE acc.user.email = :email")
    Account findByEmailJPQL(@Param("email") String email);
}
