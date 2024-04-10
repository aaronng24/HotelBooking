package com.ASM.service;

import com.ASM.model.User;

public interface AccountService {
	User findById(String email);
}
