package com.modusoftware.sitic.phoneProtect.user.test.persistent;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.modusoftware.sitic.phoneProtect.user.UserServiceApplication;
import com.modusoftware.sitic.phoneProtect.user.persistent.PermissionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserServiceApplication.class })
public class PermissionRepositoryTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(PermissionRepositoryTest.class);
  
    @Value("${test.username.2}")
    private String username2;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Test
    public void findPermissionByUsernameTest() {    	
		List<String> permissions = this.permissionRepository.findPermissionByUsername(username2);
		
		if(permissions != null && !permissions.isEmpty()) {
			permissions.forEach(permission -> {
				LOGGER.info("Permiso: " + permission);
			});
		}
		
		Assert.notEmpty(permissions, "permissions is not empty");
    }
    
    @Test
    public void findPermissionTest() {    	
		List<Object[]> permissions = this.permissionRepository.findAllPermissionsXRole();
		
		if(permissions != null && !permissions.isEmpty()) {
			permissions.forEach(permission -> {
				for(int i=0; i<permission.length; i++ ) {
					System.out.print(permission[i] + " - ");
				}
				
				System.out.println();
			});
		}
		
		Assert.notEmpty(permissions, "permissions is not empty");
    }
}