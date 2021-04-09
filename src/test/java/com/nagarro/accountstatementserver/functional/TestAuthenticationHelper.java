package com.nagarro.accountstatementserver.functional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.accountstatementserver.controller.RequestCredentials;

public class TestAuthenticationHelper {

    public static HttpEntity<String> getAuthenticationEntity(String username, String password) throws JsonProcessingException {
        RequestCredentials authenticationUser = getAuthenticationUser(username, password);
        String authenticationBody = getBody(authenticationUser);
        HttpHeaders authenticationHeaders = getHeaders();
        return new HttpEntity<String>(authenticationBody, authenticationHeaders);
    }

    private static RequestCredentials getAuthenticationUser(String username, String password) {
        RequestCredentials creadentials = new RequestCredentials(username, password);
        return creadentials;
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private static String getBody(final RequestCredentials creadentials) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(creadentials);
    }
}
