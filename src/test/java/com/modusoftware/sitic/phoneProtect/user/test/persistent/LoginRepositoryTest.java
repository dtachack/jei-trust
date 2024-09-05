package com.modusoftware.sitic.phoneProtect.user.test.persistent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.modusoftware.commons.util.MSNetUtils;
import com.modusoftware.sitic.phoneProtect.user.UserServiceApplication;
import com.modusoftware.sitic.phoneProtect.user.persistent.sp.RegistrarLoginStoredProcedure;
import com.modusoftware.sitic.phoneProtect.user.persistent.sp.RegistrarLogoutStoredProcedure;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserServiceApplication.class })
public class LoginRepositoryTest {

    @Value("${test.username.1}")
    private String username1;
    
    @Value("${pp.ws.user.login.session.diff.mins:30}")
	private long validaDiffMins;
    
    @Autowired
    private RegistrarLoginStoredProcedure loginSp;
    
    @Autowired
    private RegistrarLogoutStoredProcedure logoutSp;
    
    @Test
    public void registrarLoginTest() {
    	int result = this.loginSp.execute(username1, validaDiffMins, MSNetUtils.getIP()).intValue();
    	
		Assert.notNull(result, "Result is null");
		Assert.isTrue(result == 0 || result == 1, "Result is invalid");
    }
    
    @Test
    public void registrarLogoutTest() {
    	this.logoutSp.execute(username1);
    	
		assert true;
    }
}