package dev.project.authserver.dto;

import lombok.Data;

@Data
public class LoginDto {
    // Credentials
    private String username;
    private String password;
}
