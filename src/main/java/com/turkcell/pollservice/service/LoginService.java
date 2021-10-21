package com.turkcell.pollservice.service;

import com.turkcell.pollservice.model.request.LoginRequest;
import com.turkcell.pollservice.model.response.LoginResponse;
import org.springframework.stereotype.Service;


@Service
public interface LoginService {

    LoginResponse login(LoginRequest request);

}
