package com.snopko.RestApi.cars.controller;

import com.snopko.RestApi.cars.logic.dto.CarCreateDto;
import com.snopko.RestApi.security.config.SecurityConstants;
import com.snopko.RestApi.security.config.logic.dto.LoginDto;
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

import java.sql.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class CarControllerTest {
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    @Value("${default.admin.username}")
    private String testUsername;
    @Value("${default.admin.password}")
    private String testPassword;
    private CarCreateDto testCarDto = new CarCreateDto("11111111er11", "Mazda", "CX5", "Sedan", "manual", "petrol", new Date(2008, 10, 10), new Date(2023, 10, 10), new Date(2023, 10, 10));
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
    @DisplayName("Create new car")
    public void create_car() {
        ValidatableResponse vr = RestAssured.given(specification)
                .with()
                .body(testCarDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/cars/")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
        id = Long.parseLong(vr.extract().path("id").toString());
    }

    @Test
    @Order(2)
    @DisplayName("get car by valid id")
    public void get_car_by_id_ok() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/cars/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(3)
    @DisplayName("get car by invalid id")
    public void get_car_by_id_not_found() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/cars/123456")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(4)
    @DisplayName("get all cars")
    public void get_all() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/cars/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }


    @Test
    @Order(5)
    @DisplayName("get count of car")
    public void get_car_count() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("api/cars/count")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(6)
    @DisplayName("update car by valid id")
    public void update_car() {
        testCarDto.setTransmission("dsg");
        RestAssured.given(specification)
                .contentType(ContentType.JSON).body(testCarDto)
                .when()
                .patch("api/cars/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(7)
    @DisplayName("update car by invalid id")
    public void update_car_invalid() {
        testCarDto.setTransmission("error");
        RestAssured.given(specification)
                .contentType(ContentType.JSON).body(testCarDto)
                .when()
                .patch("api/cars/98745632")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(8)
    @DisplayName("delete car by id")
    public void delete_car() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .delete("api/cars/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @Order(9)
    @DisplayName("delete all cars")
    public void delete_all() {
        RestAssured.given(specification)
                .contentType(ContentType.JSON)
                .when()
                .delete("api/cars/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }
}
