package com.ASM.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ASM.model.Wallet;

public interface WalletDAO extends JpaRepository<Wallet, Integer>{
	@Query(value="update wallet set credit = credit - ?1 where email = ?2",nativeQuery=true)
	void updateWalletAfterPurchase(Double value,String email);

	@Query("select o from Wallet o where o.user.email = ?1")
	Wallet findByUserEmail(String email);
	
}
