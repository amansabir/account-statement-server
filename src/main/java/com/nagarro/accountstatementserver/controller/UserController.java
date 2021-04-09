package com.nagarro.accountstatementserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.accountstatementserver.exception.GenericRuntimeException;
import com.nagarro.accountstatementserver.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public String signIn(@RequestBody RequestCredentials credentials) {

        return userService.signin(credentials.getUsername(), credentials.getPassword());
    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOut(HttpServletRequest request) {

        if (userService.signOutUser(request)) {
            return new ResponseEntity<String>("Signout successful", HttpStatus.OK);
        } else {
            throw new GenericRuntimeException("Not able to signout user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
