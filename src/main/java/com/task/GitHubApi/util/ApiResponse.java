package com.task.GitHubApi.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class ApiResponse {
    final static RestTemplate restTemplate = new RestTemplate();

    /**
     * Sends an HTTP GET request to the specified URL and retrieves the response body as a list of maps.
     *
     * @param url The URL to send the GET request to.
     * @return A list of maps representing the JSON response body.
     */
    public static List<Map<String, Object>> getBody(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                }
        ).getBody();
    }
}
