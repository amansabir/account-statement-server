package com.nagarro.accountstatementserver.functional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nagarro.accountstatementserver.AccountStatementServerApplication;

@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccountStatementServerApplication.class)
public class UserRestEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String SIGNIN_URI = "/users/signin";

    private static final String SIGNOUT_URI = "/users/signout";

    private static final String USER = "user";

    private static final String ADMIN = "admin";

    @Test
    void givenInvalidCredentialsShouldReturnAccessDenied() throws RestClientException, JsonProcessingException {

        ResponseEntity<String> authenticationResponse = restTemplate.exchange(SIGNIN_URI, HttpMethod.POST,
                TestAuthenticationHelper.getAuthenticationEntity(USER, ADMIN), String.class);
        assertThat(authenticationResponse.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void givenInvalidTokenShouldReturnForbidden() throws RestClientException, JsonProcessingException {

        String token = "Bearer " + "Invalid Token";
        HttpHeaders headers = TestAuthenticationHelper.getHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);

        ResponseEntity<String> authenticationResponse = restTemplate.exchange(SIGNIN_URI, HttpMethod.POST, jwtEntity,
                String.class);
        assertThat(authenticationResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void userShouldBeAbleToSignout() throws JsonProcessingException {

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(USER, USER);
        restTemplate.exchange(SIGNOUT_URI, HttpMethod.POST, jwtEntity, String.class);
        ResponseEntity<String> testResponse = restTemplate.exchange("/api/account/1201/statement", HttpMethod.GET,
                jwtEntity, String.class);

        assertThat(testResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    private HttpEntity<String> getAuthenticationToken(String username, String password) throws JsonProcessingException {

        HttpEntity<String> authenticationEntity = TestAuthenticationHelper.getAuthenticationEntity(username, password);

        ResponseEntity<String> authenticationResponse = restTemplate.exchange(SIGNIN_URI, HttpMethod.POST,
                authenticationEntity, String.class);

        if (authenticationResponse
                .getStatusCode()
                .equals(HttpStatus.OK)) {
            String token = "Bearer " + authenticationResponse.getBody();
            HttpHeaders headers = TestAuthenticationHelper.getHeaders();
            headers.set("Authorization", token);
            HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
            return jwtEntity;
        }

        return new HttpEntity<>("");
    }

}
