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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nagarro.accountstatementserver.AccountStatementServerApplication;

@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccountStatementServerApplication.class)
class RestEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String SIGNIN_URI = "/users/signin";

    private static final String USER = "user";

    private static final String ADMIN = "admin";

    @Test
    void givenAccountIdShouldReturnStatementForThreeMonthsForAccountId() throws JsonProcessingException {
        String accountId = "1201";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(USER, USER);
        ResponseEntity<String> response = restTemplate.exchange("/api/account/" + accountId + "/statement",
                HttpMethod.GET, jwtEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void givenAccountIdAndDateRangeShouldAsUserShouldReturn401Error() throws JsonProcessingException {
        String accountId = "1201";
        String fromDate = "2021-01-01";
        String toDate = "2020-02-01";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(USER, USER);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/account/" + accountId + "/statement?fromDate=" + fromDate + "&toDate=" + toDate, HttpMethod.GET,
                jwtEntity, String.class);
    }

    @Test
    void givenAccountIdAndDateRangeShouldReturnStatementForAccountIdAndDateRange() throws JsonProcessingException {
        String accountId = "1201";
        String fromDate = "2021-01-01";
        String toDate = "2020-02-01";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(ADMIN, ADMIN);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/account/" + accountId + "/statement?fromDate=" + fromDate + "&toDate=" + toDate, HttpMethod.GET,
                jwtEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void givenAccountIdAndAmountRangeShouldReturnStatementForAccountIdAndAmountRange() throws JsonProcessingException {
        String accountId = "1201";
        String toAmount = "1000";
        String fromAmount = "10000";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(ADMIN, ADMIN);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/account/" + accountId + "/statement?fromAmount=" + fromAmount + "&toAmount=" + toAmount,
                HttpMethod.GET, jwtEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void givenAccountIdAndAmountRangeShouldReturnStatementForAccountIdAndAmountRangeAndForDateRange()
            throws JsonProcessingException {
        String accountId = "1201";
        String fromDate = "2021-01-01";
        String toDate = "2020-02-01";
        String toAmount = "1000";
        String fromAmount = "10000";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(ADMIN, ADMIN);

        ResponseEntity<String> response = restTemplate
                .exchange(
                        "/api/account/" + accountId + "/statement?fromAmount=" + fromAmount + "&toAmount=" + toAmount
                                + "&fromDate=" + fromDate + "&toDate=" + toDate,
                        HttpMethod.GET, jwtEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void givenInvalidAccountIdShouldSendValidResponse() throws JsonProcessingException {
        String accountId = "";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(USER, USER);

        ResponseEntity<String> response = restTemplate.exchange("/api/account/" + accountId + "/statement",
                HttpMethod.GET, jwtEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void givenInvalidToDateShouldSendProperMessage() throws JsonProcessingException {
        String accountId = "1201";
        String fromDate = "2021-01-01";
        String toDate = "2020-02-q";

        HttpEntity<String> jwtEntity = this.getAuthenticationToken(ADMIN, ADMIN);

        ResponseEntity<String> response = this.restTemplate.exchange(
                "/api/account/" + accountId + "/statement?fromDate=" + fromDate + "&toDate=" + toDate, HttpMethod.GET,
                jwtEntity, String.class);

        String expectedMessage = "Statement Date Format is not valid. Supported format";

        assertThat(response.getBody()).contains(expectedMessage);

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
