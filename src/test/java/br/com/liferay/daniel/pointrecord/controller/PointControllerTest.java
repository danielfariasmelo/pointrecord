package br.com.liferay.daniel.pointrecord.controller;

import br.com.liferay.daniel.pointrecord.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@PointTest
@Sql(value = "classpath:point_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PointControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createRegisterSuccessfullyTest() throws Exception {

        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("12083924837");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange("/api/clockin/create", HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.OK, createRegister.getStatusCode());
        TestPrint.printTest(createRegister);
    }

    @Test
    public void createRegisterUnSuccessfullyTest() throws Exception {

        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("2");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange("/api/clockin/create", HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createRegister.getStatusCode());
        TestPrint.printTest(createRegister);
    }

    @Test
    public void createRegisterLimitTimeUnSuccessfullyTest() throws Exception {


    }

    @Test
    public void createRegisterLimitTimeSuccessfullyTest() throws Exception {


    }

    @Test
    public void createRegisterExistSuccessfullyTest() throws Exception {


    }


}
