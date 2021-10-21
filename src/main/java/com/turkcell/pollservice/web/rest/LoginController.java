package com.turkcell.pollservice.web.rest;

import com.turkcell.pollservice.model.request.LoginRequest;
import com.turkcell.pollservice.model.response.LoginResponse;
import com.turkcell.pollservice.model.response.TokenResponse;
import com.turkcell.pollservice.service.LoginService;
import com.turkcell.pollservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(loginService.login(request));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> tokenValidate(@RequestParam("token") String token) {

        return  ResponseEntity.ok().body(jwtUtil.validateTokenWithAuth(token));
    }
}
