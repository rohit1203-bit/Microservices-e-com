package dev.project.authserver.controller;

import dev.project.authserver.dto.LoginDto;
import dev.project.authserver.dto.ProfileDto;
import dev.project.authserver.dto.RegisterDto;
import dev.project.authserver.dto.TokenDto;
import dev.project.authserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@RestController
@RequestMapping("/api/v1/auth/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    public TokenDto register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @GetMapping("/profile")
    public ProfileDto profile() {
        return authService.getProfile();
    }

    @PutMapping("/profile/edit")
    public void editProfile() {
        // TODO: add edit functionality
    }

}
