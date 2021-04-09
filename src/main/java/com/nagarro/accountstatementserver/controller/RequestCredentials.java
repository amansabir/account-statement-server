package com.nagarro.accountstatementserver.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestCredentials {

    @Getter
    private String username;

    @Getter
    private String password;

    public RequestCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
