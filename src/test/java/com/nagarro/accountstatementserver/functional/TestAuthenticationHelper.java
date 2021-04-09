package com.nagarro.accountstatementserver.functional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.accountstatementserver.controller.RequestCredentials;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TestAuthenticationHelper {

    public static HttpEntity<String> getAuthenticationEntity(String username, String password) throws JsonProcessingException {
        RequestCredentials authenticationUser = getAuthenticationUser(username, password);
        String authenticationBody = getBody(authenticationUser);
        HttpHeaders authenticationHeaders = getHeaders();
        return new HttpEntity<>(authenticationBody, authenticationHeaders);
    }

    private static RequestCredentials getAuthenticationUser(String username, String password) {
        return new RequestCredentials(username, password);
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private static String getBody(final RequestCredentials credentials) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(credentials);
    }
}
