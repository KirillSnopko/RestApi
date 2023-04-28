package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.SecurityConstants;
import com.snopko.RestApi.security.logic.dto.AuthResponseDto;
import com.snopko.RestApi.security.logic.dto.LoginDto;
import com.snopko.RestApi.security.logic.dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
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
    private final UserDto userDto = new UserDto("testUser3", "password");
    private RequestSpecification specification;

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
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
                .extract()
                .body()
                .jsonPath()
                .get("accessToken");

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
    @DisplayName("delete")
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

