package br.com.liferay.daniel.pointrecord.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

public class TestPrint {

    public static void printTest(ResponseEntity tResponseEntity){
        System.out.println("\n");
        System.out.println("Log : =============================================================================================");
        System.out.println("Status: "+asJsonString(tResponseEntity.getStatusCode()));
        System.out.println("Headers : " +asJsonString(tResponseEntity.getHeaders()));
        System.out.println("Class : " + asJsonString(tResponseEntity.getBody().getClass().getName()));
        System.out.println("Object: "+(char)27 + "[31m"+asJsonString(tResponseEntity.getBody())+"" + (char)27 + "[0m");
        System.out.println("End Log : =========================================================================================");
        System.out.println("\n");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}