package com.modusoftware.sitic.phoneProtect.user.test.ws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.modusoftware.sitic.phoneProtect.user.UserServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServiceApplication.class}, webEnvironment=WebEnvironment.DEFINED_PORT)
public class RoleRestServiceTest {

    @Value("${test.ws.role.url}")
    private String urlMain;

    @Value("${authentication.basic.username}")
    private String usernameAuth;

    @Value("${authentication.basic.password}")
    private String passwordAuth;

    @Test
    public void findAll_ok_test() {
        RestAssured
		 .given()
		     .log().all()
		     .contentType(ContentType.JSON)
		     .and()
		     .authentication().basic(this.usernameAuth, this.passwordAuth)               
		 .when()
		     .get(this.urlMain)
		 .then()
		     .log().all()
		     .statusCode(HttpStatus.OK.value());
       
    }
}
