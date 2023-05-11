package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.SecurityConstants;
import com.snopko.RestApi.security.dao.entity.AppRole;
import com.snopko.RestApi.security.config.logic.dto.AdminDtoCreate;
import com.snopko.RestApi.security.config.logic.dto.LoginDto;
import com.snopko.RestApi.security.config.logic.facade.AdminFacade;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerTest {
    @Autowired
    AdminFacade service;
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    @Value("${default.admin.username}")
    private String username;
    @Value("${default.admin.password}")
    private String password;
    private final AdminDtoCreate newAdmin = new AdminDtoCreate("admin1", "admin1", "admin1@gmail.com", AppRole.ADMIN);
    private long newAdminId;
    private RequestSpecification specification;

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @DisplayName("login OK")
    public void login_200() {
        LoginDto login = new LoginDto(username, password);
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
    @Order(2)
    @DisplayName("Create user. Ok")
    public void create_200() {
        ValidatableResponse vr = RestAssured.given(specification)
                .with()
                .body(newAdmin)
                .contentType(ContentType.JSON)
                .when()
                .post("api/admin/")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);

        newAdminId = Long.parseLong(vr.extract().path("id").toString());
    }

    @Test
    @Order(3)
    @DisplayName("get all users")
    public void getAll() {
        RestAssured.given(specification)
                .with()
                .contentType(ContentType.JSON)
                .when()
                .get("api/admin/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @DisplayName("update user")
    public void update() {
        RestAssured.given(specification)
                .with()
                .body(newAdmin)
                .contentType(ContentType.JSON)
                .when()
                .put("api/admin/" + newAdminId)
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    @Order(5)
    @DisplayName("get user by id")
    public void getById() {
        RestAssured.given(specification)
                .with()
                .contentType(ContentType.JSON)
                .when()
                .get("api/admin/" + newAdminId)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(6)
    @DisplayName("delete user by id")
    public void delete() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .delete("api/admin/" + newAdminId)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }
}
