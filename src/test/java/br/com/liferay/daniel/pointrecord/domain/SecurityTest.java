package br.com.liferay.daniel.pointrecord.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

public class SecurityTest {

    public static HttpHeaders getHeader (){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic MTIwODM5MjQ4Mzc6MTIz");
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        return headers;
    }
}
