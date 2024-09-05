package com.modusoftware.sitic.phoneProtect.user.test.persistent;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.modusoftware.sitic.phoneProtect.domain.constant.BooleanValues;
import com.modusoftware.sitic.phoneProtect.entity.UserEntity;
import com.modusoftware.sitic.phoneProtect.user.UserServiceApplication;
import com.modusoftware.sitic.phoneProtect.user.persistent.RoleRepository;
import com.modusoftware.sitic.phoneProtect.user.persistent.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserServiceApplication.class })
public class UserRepositoryTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);
  
    @Value("${test.username.1}")
    private String username1;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Test
    public void findByUsernameTest() {
    	UserEntity userQuery = this.userRepository.findByUsername(username1);
		
    	LOGGER.info(userQuery.toString());
    	
		Assert.notNull(userQuery, "UserEntity is null");
    }
    
    private UserEntity getUserEntity() {
    	UserEntity user = new UserEntity();
    	user.setUsername(username1);
    	user.setStatus(BooleanValues.YES.getCode());
    	user.setCreationDate(new Date());
    	user.setRole(this.roleRepository.findAll().iterator().next());
    	
    	return user;
    }
}