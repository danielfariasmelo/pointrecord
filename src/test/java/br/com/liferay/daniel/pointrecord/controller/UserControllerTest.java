package br.com.liferay.daniel.pointrecord.controller;

import br.com.liferay.daniel.pointrecord.domain.PointTest;
import br.com.liferay.daniel.pointrecord.domain.ResultDTO;
import br.com.liferay.daniel.pointrecord.domain.SecurityTest;
import br.com.liferay.daniel.pointrecord.domain.TestPrint;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@PointTest
@Sql(value = "classpath:user.sql")
@Sql(value = "classpath:user_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String GET_WORK_USER = "/api/user/{pis}/works/{periodIni}/{periodFin}";

    /**
     * This method has the purpose of validating if the calculation of the beats within the common schedules
     * are being calculated correctly
     *
     * EXAMPLES :
     * 08:00 -> 12:00 -> 04:00 -> 240MIN
     * 13:00 -> 18:00 -> 05:00 -> 300MIN
     * TOTAL : 540 MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkWeekSuccessfullyTest() throws Exception {

        final LocalDate initialDay = LocalDate.of(2018,06,25);
        final LocalDate finalDay = LocalDate.of(2018,06,25);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(540),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method aims to validate if the calculation of the rest for weekdays are being calculated correctly.
     *
     * EXAMPLES :
     * 08:00 -> 12:00 -> 04:00 -> 240MIN
     * 13:00 -> 18:00 -> 05:00 -> 300MIN
     * TOTAL : 540 MIN
     *
     * @throws Exception
     */
    @Test
    public void userRestWeekSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,06,25);
        final LocalDate finalDay = LocalDate.of(2018,06,25);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(540),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method has as objective to validate the calculation of the rest of the users, and validating the obligation
     *
     * EXAMPLES:
     * 08:00 -> 12:00 -> 04:00 -> 240MIN
     * 12:30 -> 18:00 -> 05:00 -> 300MIN
     * TOTAL: 570 MIN
     *
     * @throws Exception
     */
    @Test
    public void userRestRequiredWeekSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,06,26);
        final LocalDate finalDay = LocalDate.of(2018,06,26);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(570),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(30),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method aims to validate the calculation of overtime worked together with normal hours.
     *
     * EXAMPLES:
     * 04:00 -> 06:00 -> 02:00 -> 120MIN  -> 144
     * 06:00 -> 12:00 -> 06:00 -> 360MIN
     * 13:00 -> 22:00 -> 09:00 -> 540MIN
     * 22:00 -> 23:00 -> 01:00 -> 60MIN -> 72
     * TOTAL: 1116 MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkWeekExtraBeginSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,02);
        final LocalDate finalDay = LocalDate.of(2018,07,02);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(1116),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method aims to validate the calculation of hours worked in case of lack of point recording.
     *
     * EXAMPLES:
     * 08:00 -> 12:00 -> 04:00 -> 240MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkWeekWithoutPointExitSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,06,27);
        final LocalDate finalDay = LocalDate.of(2018,06,27);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(240),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method has the purpose of validating if the calculation of the beats within the common schedules
     * are being calculated correctly
     *
     * EXAMPLES :
     * 08:00 -> 12:00 -> 04:00 -> 240MIN -> 360 MIN
     * 13:00 -> 18:00 -> 05:00 -> 300MIN -> 450 MIN
     * TOTAL : 810 MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkSaturdaySuccessfullyTest() throws Exception {

        final LocalDate initialDay = LocalDate.of(2018,06,23);
        final LocalDate finalDay = LocalDate.of(2018,06,23);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(810),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method aims to validate if the calculation of the rest for weekdays are being calculated correctly.
     *
     * EXAMPLES :
     * 08:00 -> 12:00 -> 04:00 -> 240MIN -> 360 MIN
     * 13:00 -> 18:00 -> 05:00 -> 300MIN -> 450 MIN
     * TOTAL :810 MIN
     *
     * @throws Exception
     */
    @Test
    public void userRestSaturdaySuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,06,30);
        final LocalDate finalDay = LocalDate.of(2018,06,30);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(810),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method has as objective to validate the calculation of the rest of the users, and validating the obligation
     *
     * EXAMPLES:
     * 08:00 -> 12:00 -> 04:00 -> 240MIN -> 360 MIN
     * 12:30 -> 18:00 -> 05:30 -> 330MIN -> 495 MIN
     * TOTAL : 855 MIN
     *
     * @throws Exception
     */
    @Test
    public void userRestRequiredSaturdaySuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,07);
        final LocalDate finalDay = LocalDate.of(2018,07,07);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(855),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(30),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method aims to validate the calculation of overtime worked together with normal hours.
     *
     * EXAMPLES:
     * 04:00 -> 06:00 -> 02:00 -> 120MIN  -> 216 MIN
     * 06:00 -> 12:00 -> 06:00 -> 360MIN  -> 540 MIN
     * 13:00 -> 22:00 -> 09:00 -> 540MIN  -> 810 MIN
     * 22:00 -> 23:00 -> 01:00 -> 60MIN   -> 108 MIN
     * TOTAL : 1674 MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkSaturdayExtraBeginSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,21);
        final LocalDate finalDay = LocalDate.of(2018,07,21);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(1674),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method aims to validate the calculation of hours worked in case of lack of point recording.
     *
     * EXAMPLES:
     * 08:00 -> 12:00 -> 04:00 -> 240MIN -> 360MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkSaturdayWithoutPointExitSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,14);
        final LocalDate finalDay = LocalDate.of(2018,07,14);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(360),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method has the purpose of validating if the calculation of the beats within the common schedules
     * are being calculated correctly
     *
     * EXAMPLES :
     * 08:00 -> 12:00 -> 04:00 -> 240MIN  -> 480 MIN
     * 13:00 -> 18:00 -> 05:00 -> 300MIN  -> 600 MIN
     * TOTAL :1080 MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkSundaySuccessfullyTest() throws Exception {

        final LocalDate initialDay = LocalDate.of(2018,06,24);
        final LocalDate finalDay = LocalDate.of(2018,06,24);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(1080),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        TestPrint.printTest(responseEntity);
    }
    /**
     * This method aims to validate if the calculation of the rest for weekdays are being calculated correctly.
     *
     * EXAMPLES :
     * 08:00 -> 12:00 -> 04:00 -> 240MIN  -> 480 MIN
     * 13:00 -> 18:00 -> 05:00 -> 300MIN  -> 600 MIN
     * TOTAL: 1080 MIN
     *
     * @throws Exception
     */
    @Test
    public void userRestSundaySuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,01);
        final LocalDate finalDay = LocalDate.of(2018,07,01);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(1080),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method has as objective to validate the calculation of the rest of the users, and validating the obligation
     *
     * EXAMPLES:
     * 08:00 -> 12:00 -> 04:00 -> 240MIN  -> 480 MIN
     * 12:30 -> 18:00 -> 05:00 -> 330MIN  -> 660 MIN
     * TOTAL: 1140 MIN
     *
     * @throws Exception
     */
    @Test
    public void userRestRequiredSundaySuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,8);
        final LocalDate finalDay = LocalDate.of(2018,07,8);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(1140),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(30),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method aims to validate the calculation of overtime worked together with normal hours.
     *
     * EXAMPLES:
     * 04:00 -> 06:00 -> 02:00 -> 120MIN  -> 288 MIN
     * 06:00 -> 12:00 -> 06:00 -> 360MIN  -> 720 MIN
     * 13:00 -> 22:00 -> 09:00 -> 540MIN  -> 1080 MIN
     * 22:00 -> 23:00 -> 01:00 -> 60MIN   -> 144 MIN
     * TOTAL: 2592 MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkSundayExtraBeginSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,22);
        final LocalDate finalDay = LocalDate.of(2018,07,22);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(2232),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(0),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }

    /**
     * This method aims to validate the calculation of hours worked in case of lack of point recording.
     *
     * EXAMPLES:
     * 08:00 -> 12:00 -> 04:00 -> 240MIN -> 480MIN
     *
     * @throws Exception
     */
    @Test
    public void userWorkSundayWithoutPointExitSuccessfullyTest() throws Exception {
        final LocalDate initialDay = LocalDate.of(2018,07,15);
        final LocalDate finalDay = LocalDate.of(2018,07,15);
        final String pis = "12083924837";

        ResponseEntity<ResultDTO> responseEntity = restTemplate
                .exchange(GET_WORK_USER, HttpMethod.GET, new HttpEntity<>(SecurityTest.getHeader()), ResultDTO.class,pis,
                        initialDay,finalDay);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNotNull();
        Assert.assertEquals(new Double(480),responseEntity.getBody().getWorkPeriod());
        Assert.assertEquals(1,responseEntity.getBody().getUserWorkList().size());
        Assert.assertEquals(new Double(30),responseEntity.getBody().getUserWorkList().get(0).getRestRequired());
        TestPrint.printTest(responseEntity);
    }


}
