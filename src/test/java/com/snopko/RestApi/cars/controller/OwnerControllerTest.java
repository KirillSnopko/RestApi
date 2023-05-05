package com.snopko.RestApi.cars.controller;

import com.snopko.RestApi.cars.logic.dto.OwnerCreateDto;
import com.snopko.RestApi.security.config.SecurityConstants;
import com.snopko.RestApi.security.logic.dto.LoginDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OwnerControllerTest {
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    @Value("${default.admin.username}")
    private String testUsername;
    @Value("${default.admin.password}")
    private String testPassword;
    private OwnerCreateDto testOwnerDto = new OwnerCreateDto("Snopko", "Kirill");
    private long id;
    private RequestSpecification specification;

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;

        LoginDto login = new LoginDto(testUsername, testPassword);
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
    @Order(1)
    @RepeatedTest(5)
    @DisplayName("Create new owner")
    public void create() {
        ValidatableResponse vr = RestAssured.given(specification)
                .with()
                .body(testOwnerDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/owners")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
        id = Long.parseLong(vr.extract().path("id").toString());
    }

    @Test
    @Order(2)
    @DisplayName("get owner by valid id")
    public void get_by_id_ok() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/owners/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @DisplayName("get owner by invalid id")
    public void get_by_id_not_found() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/owners/123456")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(4)
    @DisplayName("get all owners")
    public void get_all() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/owners/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }


    @Test
    @Order(5)
    @DisplayName("get count of owners")
    public void get_count() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/owners/count")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(6)
    @DisplayName("update owner by valid id")
    public void update() {
        testOwnerDto.setSecondName("Vova");
        RestAssured.given(specification)
                .contentType(ContentType.JSON).body(testOwnerDto)
                .when()
                .patch("api/owners/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(7)
    @DisplayName("update owner by invalid id")
    public void update_invalid() {
        testOwnerDto.setSecondName("error");
        RestAssured.given(specification)
                .contentType(ContentType.JSON).body(testOwnerDto)
                .when()
                .patch("api/owners/98745632")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(8)
    @DisplayName("delete owner by id")
    public void delete() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .delete("api/owners/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @Order(9)
    @DisplayName("delete all owners")
    public void delete_all() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .delete("api/owners/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }
}
