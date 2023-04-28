package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.dao.entity.RoleDao;
import com.snopko.RestApi.security.logic.dto.AdminDto;
import com.snopko.RestApi.security.logic.service.AdminService;
import io.restassured.RestAssured;
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
public class AdminControllerTest {
    @Autowired
    AdminService service;
    @LocalServerPort
    private Integer port;
    private final String host = "http://localhost";
    private final AdminDto userDto = new AdminDto(RoleDao.ADMIN);
    private RequestSpecification specification;

    @BeforeAll
    public void init() {
        RestAssured.baseURI = host;
        RestAssured.port = port;
        AdminDto admin = new AdminDto(RoleDao.ADMIN);
        admin.setPassword("admin");
        admin.setUsername("admin");
        service.create(admin);

    }


    @AfterAll
    public void destroy() {


    }
}
