package com.snopko.RestApi.controller;

import com.snopko.RestApi.logic.dto.OwnerDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private OwnerDto testOwnerDto = new OwnerDto("Snopko", "Kirill");
    private long id;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @RepeatedTest(5)
    @DisplayName("Create new owner")
    public void create() {
        ValidatableResponse vr = RestAssured.with()
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
        RestAssured.given()
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
        RestAssured.given()
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
        RestAssured.given()
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
        RestAssured.given()
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
        RestAssured.given()
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
        RestAssured.given()
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
        RestAssured.given()
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
        RestAssured.given()
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
