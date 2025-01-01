package com.attendance.Faculty.Restemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiClient {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/attendance/mark";

        // Create the request payload
        String requestJson = "{ \"studentId\": 1, \"status\": \"Present\" }";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Print the response
        System.out.println(response.getBody());
    }
}
