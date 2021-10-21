package com.turkcell.pollservice.service.impl;

import com.turkcell.pollservice.exception.AuthException;
import com.turkcell.pollservice.model.PollUser;
import com.turkcell.pollservice.model.request.LoginRequest;
import com.turkcell.pollservice.model.response.LoginResponse;
import com.turkcell.pollservice.service.LoginService;
import com.turkcell.pollservice.service.UserAuthenticator;
import com.turkcell.pollservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserAuthenticator authenticator;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        LoginResponse.LoginResponseBuilder builder = LoginResponse.builder();
        try {
            PollUser user = authenticator.authenticate(request.getUsernameOrEmail(), request.getPassword());
            String token = jwtUtil.generateToken(user);
            builder.success(true).accessToken(token).user(user);
        } catch (AuthException e) {
            builder.success(false);
            throw new RuntimeException("Lütfen bilgilerinizi kontrol edin.");
        }
        return builder.build();
    }

}
