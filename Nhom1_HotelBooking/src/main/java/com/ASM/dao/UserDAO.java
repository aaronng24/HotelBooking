package com.ASM.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ASM.model.User;

public interface UserDAO extends JpaRepository<User, String>{
	@Query("SELECT o FROM User o WHERE o.email LIKE ?1")
	Page<User> findAllByEmailLike(String keywords, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);
	
	boolean existsByEmail(String email);
}
