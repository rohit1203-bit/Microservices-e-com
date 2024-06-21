package dev.project.authserver.service;

import dev.project.authserver.dto.LoginDto;
import dev.project.authserver.dto.ProfileDto;
import dev.project.authserver.dto.RegisterDto;
import dev.project.authserver.dto.TokenDto;
import dev.project.authserver.model.User;
import dev.project.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public TokenDto login(LoginDto loginDto) {
        String userName = loginDto.getUsername();
        String password = loginDto.getPassword();

        var user = userRepository.findByUsername(userName);
        if(user.isEmpty()) throw new UsernameNotFoundException("User Not Found");

        if(!passwordEncoder.matches(password, user.get().getPassword()))
            throw new BadCredentialsException("Password does not match");

        String token = jwtService.createToken(userName, user.get().getId());
        return TokenDto.builder().status(true).token(token).build();
    }

    public TokenDto register(RegisterDto registerDto) {
        String encodedPass = passwordEncoder.encode(registerDto.getPassword());

        var user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .address(registerDto.getAddress())
                .phone(registerDto.getPhone())
                .username(registerDto.getUsername())
                .password(encodedPass)
                .build();

        userRepository.save(user);
        var addedUser = userRepository.findByUsername(user.getUsername());
        if(addedUser.isEmpty()) throw new UsernameNotFoundException("User Not Found");
        String token = jwtService.createToken(registerDto.getUsername(), addedUser.get().getId());

        return TokenDto.builder().status(true).token(token).build();
    }

    public ProfileDto getProfile() {
        var username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        var optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found");
        var user = optionalUser.get();
        return ProfileDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }
}
