package com.nagarro.accountstatementserver.service;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.nagarro.accountstatementserver.domain.repository.UserRepository;
import com.nagarro.accountstatementserver.exception.GenericRuntimeException;
import com.nagarro.accountstatementserver.security.JwtTokenProvider;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username,
                    Collections.singletonList(userRepository
                            .findByUsername(username)
                            .getRole()));
        } catch (AuthenticationException e) {
            throw new GenericRuntimeException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public boolean signOutUser(HttpServletRequest request) {
        return this.jwtTokenProvider.addTokenToInvalidCache(request);
    }
}