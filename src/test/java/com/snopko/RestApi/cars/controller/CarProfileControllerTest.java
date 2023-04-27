package com.snopko.RestApi.cars.controller;

import com.snopko.RestApi.cars.logic.dto.CarDto;
import com.snopko.RestApi.cars.logic.dto.CarProfileDtoCreate;
import com.snopko.RestApi.cars.logic.dto.OwnerDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarProfileControllerTest {
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    private final CarDto testCarDto = new CarDto("11111111er11", "Mazda", "CX5", "Sedan", "manual", "petrol", new Date(2008, 10, 10), new Date(2023, 10, 10), new Date(2023, 10, 10));
    private final OwnerDto testOwnerDto = new OwnerDto("Snopko", "Kirill");
    private CarProfileDtoCreate testProfileDto = new CarProfileDtoCreate();
    private long id;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @DisplayName("Create car and owner")
    public void create_init() {
        ValidatableResponse vr = RestAssured.with()
                .body(testCarDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/cars")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
        testProfileDto.setCarId(Long.parseLong(vr.extract().path("id").toString()));

        vr = RestAssured.with()
                .body(testOwnerDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/owners")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
        testProfileDto.setOwnerId(Long.parseLong(vr.extract().path("id").toString()));
    }

    @Test
    @Order(2)
    @DisplayName("Create new profile")
    public void create() {
        testProfileDto.setNumber("0000 AA-7");
        System.err.println(testProfileDto.getCarId());
        System.err.println(testProfileDto.getOwnerId());
        ValidatableResponse vr = RestAssured.with()
                .body(testProfileDto)
                .contentType(ContentType.JSON)
                .when()
                .post("api/profiles")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
        id = Long.parseLong(vr.extract().path("id").toString());
    }

    @Test
    @Order(3)
    @DisplayName("get profile by valid id")
    public void get_by_id_ok() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/profiles/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(4)
    @DisplayName("get profile by invalid id")
    public void get_by_id_not_found() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/profile/123456")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(5)
    @DisplayName("get all profiles")
    public void get_all() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/profiles/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }


    @Test
    @Order(6)
    @DisplayName("get count of profiles")
    public void get_count() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/profiles/count")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(7)
    @DisplayName("update profile by valid id")
    public void update() {
        testProfileDto.setNumber("1111 AA-1");
        RestAssured.given()
                .contentType(ContentType.JSON).body(testProfileDto)
                .when()
                .patch("api/profiles/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(8)
    @DisplayName("update profile by invalid id")
    public void update_invalid() {
        RestAssured.given()
                .contentType(ContentType.JSON).body(testProfileDto)
                .when()
                .patch("api/profiles/98745632")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Order(9)
    @DisplayName("delete profile by id")
    public void delete() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/profiles/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @Order(10)
    @DisplayName("delete all profiles")
    public void delete_all() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/profiles/all")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }

    @Test
    @Order(11)
    @DisplayName("delete car and owner")
    public void dismiss() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/owners/" + testProfileDto.getOwnerId())
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("api/cars/" + testProfileDto.getCarId())
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(204);
    }
}
