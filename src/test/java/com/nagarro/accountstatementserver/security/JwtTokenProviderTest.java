package com.nagarro.accountstatementserver.security;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Mock
    private CustomUserDetails customUserDetails;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

}
