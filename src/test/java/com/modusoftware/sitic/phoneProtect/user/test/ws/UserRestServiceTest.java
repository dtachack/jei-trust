package com.modusoftware.sitic.phoneProtect.user.test.ws;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.modusoftware.sitic.phoneProtect.domain.UserDTO;
import com.modusoftware.sitic.phoneProtect.domain.constant.BooleanValues;
import com.modusoftware.sitic.phoneProtect.entity.UserEntity;
import com.modusoftware.sitic.phoneProtect.user.UserServiceApplication;
import com.modusoftware.sitic.phoneProtect.user.persistent.RoleRepository;
import com.modusoftware.sitic.phoneProtect.user.persistent.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServiceApplication.class}, webEnvironment=WebEnvironment.DEFINED_PORT)
public class UserRestServiceTest {

    @Value("${test.ws.user.url}")
    private String urlMain;

    @Value("${authentication.basic.username}")
    private String usernameAuth;

    @Value("${authentication.basic.password}")
    private String passwordAuth;
    
    @Value("${test.username.1}")
    private String username1;
    
    @Value("${test.username.2}")
    private String username2;
    
    @Value("${test.username.3}")
    private String username3;
    
    @Value("${test.rol.1}")
    private String rol1;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private Gson gson;

    @Test
    public void findByUsername_ok_test() {
        RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .get(this.urlMain + "/username/" + this.username1)
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());       
    }
    
    @Test
    public void findByUsername_noContent_test() {
		RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .get(this.urlMain + "/username/" + "user_no_exist")
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    public void register_create_ok_test() {
		boolean userExists = true;
		
		UserEntity user = this.userRepository.findByUsername(this.username3);
		 
		if(user != null) {
			this.userRepository.delete(user);
		}else {
			userExists = false;
		}
         
        try {
        	RestAssured
             .given()
                 .log().all()
                 .contentType(ContentType.JSON)
                 .and()
                 .authentication().basic(this.usernameAuth, this.passwordAuth)               
             .when()
             	 .body(this.gson.toJson(this.getUserDTO()))
                 .post(this.urlMain)
             .then()
                 .log().all()
                 .statusCode(HttpStatus.OK.value());
        }catch (Exception e) {
        	if(userExists) {
				this.userRepository.save(user);
			}
		}
    }
    
    @Test
    public void register_noModify_ok_test() {
		boolean userExists = false;
		
		UserEntity user = this.userRepository.findByUsername(this.username3);
		 
		if(user == null) {
			user = this.getUserEntity();
			this.userRepository.save(user);
		}else {
			userExists = true;
		}
         
        try {
        	UserDTO dto = this.getUserDTO();
        	dto.setRoleName(user.getRole().getName());
        	
        	RestAssured
             .given()
                 .log().all()
                 .contentType(ContentType.JSON)
                 .and()
                 .authentication().basic(this.usernameAuth, this.passwordAuth)               
             .when()
             	 .body(this.gson.toJson(dto))
                 .post(this.urlMain)
             .then()
                 .log().all()
                 .statusCode(HttpStatus.OK.value());
        }finally {
        	if(!userExists) {				
				this.userRepository.delete(user);
			}
		}
    }
    
    @Test
    public void register_modify_ok_test() {
		boolean userExists = false;
		
		UserEntity user = this.userRepository.findByUsername(this.username3);
		 
		if(user == null) {
			user = this.getUserEntity();
			this.userRepository.save(user);
		}else {
			userExists = true;
		}
         
        try {
        	UserDTO dto = this.getUserDTO();
        	dto.setRoleName(this.rol1);
        	
        	RestAssured
             .given()
                 .log().all()
                 .contentType(ContentType.JSON)
                 .and()
                 .authentication().basic(this.usernameAuth, this.passwordAuth)               
             .when()
             	 .body(this.gson.toJson(dto))
                 .post(this.urlMain)
             .then()
                 .log().all()
                 .statusCode(HttpStatus.OK.value());
        }finally {
        	if(!userExists) {				
				this.userRepository.delete(user);
			}
		}
    }
    
    @Test
    public void register_noContent_ok_test() {
		boolean userExists = false;
		
		UserEntity user = this.userRepository.findByUsername(this.username3);
		 
		if(user == null) {
			user = this.getUserEntity();
			this.userRepository.save(user);
		}else {
			userExists = true;
		}
         
        try {
        	UserDTO dto = this.getUserDTO();
        	dto.setRoleName("MyRole");
        	
        	RestAssured
             .given()
                 .log().all()
                 .contentType(ContentType.JSON)
                 .and()
                 .authentication().basic(this.usernameAuth, this.passwordAuth)               
             .when()
             	 .body(this.gson.toJson(dto))
                 .post(this.urlMain)
             .then()
                 .log().all()
                 .statusCode(HttpStatus.NO_CONTENT.value());
        }finally {
        	if(!userExists) {				
				this.userRepository.delete(user);
			}
		}
    }
    
    @Test
    public void findPermissionByUsername_ok_test() {	
		RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .get(this.urlMain + "/permission/username/" + this.username2)
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());
       
    }
    
    @Test
    public void registrarLoginOkTest() {
        RestAssured
		 .given()
		     .log().all()
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .post(this.urlMain + "/login/username/" + this.username1)
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());       
    }
    
    @Test
    public void registrarLoginUsernameNotFoundErrorTest() {
        RestAssured
		 .given()
		     .log().all()
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .post(this.urlMain + "/login/username/" + "UserNotFound")
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());       
    }
    
    @Test
    public void registrarLogoutOkTest() {
        RestAssured
		 .given()
		     .log().all()
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .post(this.urlMain + "/logout/username/" + this.username1)
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());       
    }
    
    @Test
    public void registrarLogoutUsernameNotFoundErrorTest() {
        RestAssured
		 .given()
		     .log().all()
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .post(this.urlMain + "/logout/username/" + "UserNotFound")
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());       
    }
    
    private UserEntity getUserEntity() {
    	UserEntity user = new UserEntity();
    	user.setUsername(this.username3);
    	user.setStatus(BooleanValues.YES.getCode());
    	user.setCreationDate(new Date());
    	user.setRole(this.roleRepository.findAll().iterator().next());
    	return user;
    }
    
    private UserDTO getUserDTO() {
    	UserDTO user = new UserDTO();
    	user.setUsername(this.username3);
    	user.setActive(true);
    	user.setRoleName(this.roleRepository.findAll().iterator().next().getName());
    	return user;
    }
}
