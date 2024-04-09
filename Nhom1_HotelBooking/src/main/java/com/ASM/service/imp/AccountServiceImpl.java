package com.ASM.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ASM.dao.UserDAO;
import com.ASM.model.User;
import com.ASM.service.AccountService;
@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	UserDAO udao;
	@Override
	public User findById(String email) {
		// TODO Auto-generated method stub
		return udao.findById(email).get();
	}

}
