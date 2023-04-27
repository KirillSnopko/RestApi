package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.logic.dto.LoginDto;
import com.snopko.RestApi.security.logic.dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerTest {
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    private final UserDto userDto = new UserDto("test", "password");

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @DisplayName("Create user")
    public void create() {
        RestAssured.with()
                .body(userDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/auth/register")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Order(2)
    @DisplayName("login")
    public void login() {
        LoginDto login = new LoginDto("username", "password");
        String token = RestAssured.with()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("api/auth/authenticate")
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract().path("accessToken");
    }

    @Test
    @Order(3)
    @DisplayName("delete")
    public void delete() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/auth/" + userDto.getUsername())
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }
}


