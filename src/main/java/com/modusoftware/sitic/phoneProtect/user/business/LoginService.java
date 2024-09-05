package com.modusoftware.sitic.phoneProtect.user.business;

import org.springframework.stereotype.Service;

@Service
public interface LoginService {

	public int registrarLogin(String username);
	
	public void registrarLogout(String username);
	
}
