package com.modusoftware.sitic.phoneProtect.user.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.modusoftware.commons.util.MSNetUtils;
import com.modusoftware.sitic.phoneProtect.user.business.LoginService;
import com.modusoftware.sitic.phoneProtect.user.persistent.sp.RegistrarLoginStoredProcedure;
import com.modusoftware.sitic.phoneProtect.user.persistent.sp.RegistrarLogoutStoredProcedure;

@Service
@Primary
public class LoginServiceImpl implements LoginService{
	
	public static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Value("${pp.ws.user.login.session.diff.mins:30}")
	private long validaDiffMins;

    @Autowired
    private RegistrarLoginStoredProcedure loginSp;
    
    @Autowired
    private RegistrarLogoutStoredProcedure logoutSp;
    
    @Override
    public int registrarLogin(String username) {
        return this.loginSp.execute(username, validaDiffMins, MSNetUtils.getIP()).intValue();
    }
    
    @Override
    public void registrarLogout(String username) {
        this.logoutSp.execute(username);
    }
}
