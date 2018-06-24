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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@PointTest
@Sql(value = "classpath:point_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PointControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String API_POINT_CREATE = "/api/clockin/create";


    @Test
    public void createRegisterSuccessfullyTest() throws Exception {

        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("12083924837");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.OK, createRegister.getStatusCode());
        assertThat(createRegister.getBody()).isNotNull();
        assertThat(createRegister.getBody().getId()).isNotNull();
        TestPrint.printTest(createRegister);
    }

    @Test
    public void createRegisterWithoutUserUnSuccessfullyTest() throws Exception {

        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("2");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createRegister.getStatusCode());
        assertThat(createRegister.getBody().getId()).isNull();
        TestPrint.printTest(createRegister);
    }

    @Test
    public void createRegisterLimitTimeUnSuccessfullyTest() throws Exception {
        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("12083924837");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.OK, createRegister.getStatusCode());
        assertThat(createRegister.getBody()).isNotNull();
        assertThat(createRegister.getBody().getId()).isNotNull();
        TestPrint.printTest(createRegister);


        final Clockin clockinError = new Clockin();
        clockinError.setDateTime(LocalDateTime.now());
        clockinError.setPis("12083924837");

        HttpEntity<Clockin> entityReqError = new HttpEntity<>(clockinError, SecurityTest.getHeader());

        ResponseEntity<Point> createRegisterError = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReqError, Point.class);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createRegisterError.getStatusCode());
        TestPrint.printTest(createRegisterError);

    }

    @Test
    public void createRegisterLimitTimeSuccessfullyTest() throws Exception {
        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("12083924837");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.OK, createRegister.getStatusCode());
        assertThat(createRegister.getBody()).isNotNull();
        assertThat(createRegister.getBody().getId()).isNotNull();
        TestPrint.printTest(createRegister);


        final Clockin clockinError = new Clockin();
        clockinError.setDateTime(LocalDateTime.now().plusMinutes(1));
        clockinError.setPis("12083924837");

        HttpEntity<Clockin> entityReqError = new HttpEntity<>(clockinError, SecurityTest.getHeader());

        ResponseEntity<Point> createRegisterError = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReqError, Point.class);

        Assert.assertEquals(HttpStatus.OK, createRegisterError.getStatusCode());
        TestPrint.printTest(createRegisterError);

    }

    @Test
    public void createRegisterExistUnSuccessfullyTest() throws Exception {
        final Clockin clockin = new Clockin();
        clockin.setDateTime(LocalDateTime.now());
        clockin.setPis("12083924837");

        HttpEntity<Clockin> entityReq = new HttpEntity<>(clockin, SecurityTest.getHeader());

        ResponseEntity<Point> createRegister = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.OK, createRegister.getStatusCode());
        assertThat(createRegister.getBody()).isNotNull();
        assertThat(createRegister.getBody().getId()).isNotNull();
        TestPrint.printTest(createRegister);


        ResponseEntity<Point> createRegisterError = restTemplate
                .exchange(API_POINT_CREATE, HttpMethod.POST, entityReq, Point.class);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createRegisterError.getStatusCode());
        TestPrint.printTest(createRegisterError);

    }


}
