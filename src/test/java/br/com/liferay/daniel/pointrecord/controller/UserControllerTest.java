package br.com.liferay.daniel.pointrecord.controller;

import br.com.liferay.daniel.pointrecord.domain.PointTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@PointTest
@Sql(value = "classpath:user_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void userWorkSuccessfullyTest() throws Exception {

    }

    @Test
    public void userRestWorkSuccessfullyTest() throws Exception {

    }
}
