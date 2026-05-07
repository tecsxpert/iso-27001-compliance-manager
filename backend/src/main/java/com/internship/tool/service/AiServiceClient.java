package com.internship.tool.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.Duration;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AiServiceClient {

    private static final Logger logger = Logger.getLogger(AiServiceClient.class.getName());
    private static final String AI_BASE_URL = "http://localhost:5000";

    private final RestTemplate restTemplate;

    public AiServiceClient() {
        this.restTemplate = new RestTemplateBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .build();
    }

    private HttpEntity<Map<String, Object>> buildRequest(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    // Call /describe endpoint
    public String describe(String text) {
        try {
            HttpEntity<Map<String, Object>> request = buildRequest(Map.of("text", text));
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_BASE_URL + "/describe", request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.warning("AI /describe call failed: " + e.getMessage());
            return null;
        }
    }

    // Call /recommend endpoint
    public String recommend(String text) {
        try {
            HttpEntity<Map<String, Object>> request = buildRequest(Map.of("text", text));
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_BASE_URL + "/recommend", request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.warning("AI /recommend call failed: " + e.getMessage());
            return null;
        }
    }

    // Call /categorise endpoint
    public String categorise(String text) {
        try {
            HttpEntity<Map<String, Object>> request = buildRequest(Map.of("text", text));
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_BASE_URL + "/categorise", request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.warning("AI /categorise call failed: " + e.getMessage());
            return null;
        }
    }

    // Call /generate-report endpoint
    public String generateReport(String text) {
        try {
            HttpEntity<Map<String, Object>> request = buildRequest(Map.of("text", text));
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_BASE_URL + "/generate-report", request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.warning("AI /generate-report call failed: " + e.getMessage());
            return null;
        }
    }

    // Call /query endpoint
    public String query(String question) {
        try {
            HttpEntity<Map<String, Object>> request = buildRequest(Map.of("question", question));
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_BASE_URL + "/query", request, String.class);
            return response.getBody();
        } catch (Exception e) {
            logger.warning("AI /query call failed: " + e.getMessage());
            return null;
        }
    }
}
