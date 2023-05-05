package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.SecurityConstants;
import com.snopko.RestApi.security.logic.dto.LoginDto;
import com.snopko.RestApi.security.logic.dto.UserDtoCreate;
import com.snopko.RestApi.security.logic.service.UserService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserService service;
    private final String host = "http://localhost";
    private final UserDtoCreate userDto = new UserDtoCreate("testUser", "password");
    private RequestSpecification specification;

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
        if (service.existsByUsername(userDto.getUsername())) {
            service.deleteByUsername(userDto.getUsername());
        }
    }

    @Test
    @Order(1)
    @DisplayName("Create user. Ok")
    public void create_200() {
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
    @DisplayName("Create user. BadRequest")
    public void create_400() {
        RestAssured.with()
                .body(userDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/auth/register")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Order(3)
    @DisplayName("login OK")
    public void login_200() {
        LoginDto login = new LoginDto(userDto.getUsername(), userDto.getPassword());
        ValidatableResponse vr = RestAssured.with()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("api/auth/authenticate")
                .then()
                .log()
                .all()
                .statusCode(200);
        String token = vr.extract().body().jsonPath().get("accessToken");

        specification = new RequestSpecBuilder()
                .addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
                .build();
    }

    @Test
    @Order(4)
    @DisplayName("login 401")
    public void login_401() {
        LoginDto login = new LoginDto("invalid", "invalid");
        RestAssured.with()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("api/auth/authenticate")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(401);
    }

    @Test
    @Order(5)
    @DisplayName("update user")
    public void update() {
        String currentName = userDto.getUsername();
        userDto.setUsername("newUserName");
        ValidatableResponse vr = RestAssured.given(specification)
                .with()
                .body(userDto)
                .contentType(ContentType.JSON)
                .when()
                .put("api/auth/" + currentName)
                .then()
                .log()
                .all()
                .statusCode(200);
        String username = vr.extract().body().jsonPath().get("username");
        String token = vr.extract().body().jsonPath().get("accessToken");

        Assertions.assertEquals(username, userDto.getUsername());

        specification = new RequestSpecBuilder()
                .addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
                .build();
    }

    @Test
    @Order(6)
    @DisplayName("delete user")
    public void delete() {
        RestAssured.given(specification)
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


