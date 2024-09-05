package com.modusoftware.sitic.phoneProtect.user.test.ws;

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
import com.modusoftware.sitic.phoneProtect.domain.PermissionXRoleDTO;
import com.modusoftware.sitic.phoneProtect.user.UserServiceApplication;
import com.modusoftware.sitic.phoneProtect.user.persistent.PermissionRepository;
import com.modusoftware.sitic.phoneProtect.user.persistent.RoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServiceApplication.class}, webEnvironment=WebEnvironment.DEFINED_PORT)
public class PermissionRestServiceTest {

    @Value("${test.ws.permission.url}")
    private String urlMain;

    @Value("${authentication.basic.username}")
    private String usernameAuth;

    @Value("${authentication.basic.password}")
    private String passwordAuth;
    
    @Value("${test.rol.1}")
    private String rol_1;
    
    @Value("${test.username.2}")
    private String username_2;
    
    @Autowired
    private Gson gson;
    
    @Autowired
    private PermissionRepository pemPermissionRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findAllPermissionsXRole_ok_test() {
        RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .get(this.urlMain + "/x/role")
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());
       
    }
    
    @Test
    public void createUpdatePermissionsXRole_conflict_test() {
        PermissionXRoleDTO dto = new PermissionXRoleDTO(1L, null, 1L, null, 1);
    	
    	RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		 .body(this.gson.toJson(dto))
		     .post(this.urlMain + "/x/role")
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.CONFLICT.value());
       
    }     
    
    @Test
    public void createUpdatePermissionsXRole_ok_test() {
        PermissionXRoleDTO dto = new PermissionXRoleDTO();
        dto.setActive(1);
        dto.setIdPermission(this.pemPermissionRepository.findAll().iterator().next().getId());
        dto.setIdRole(this.roleRepository.findByName(rol_1).getId());
    	
    	RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		 .body(this.gson.toJson(dto))
		     .post(this.urlMain + "/x/role")
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());
       
    }         
}
