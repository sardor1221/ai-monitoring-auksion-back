package net.sardor.manitoring_ai_auksion.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sardor.manitoring_ai_auksion.service.AuthService;
import net.sardor.manitoring_ai_auksion.web.dto.AuthResponse;
import net.sardor.manitoring_ai_auksion.web.dto.LoginRequest;
import net.sardor.manitoring_ai_auksion.web.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
