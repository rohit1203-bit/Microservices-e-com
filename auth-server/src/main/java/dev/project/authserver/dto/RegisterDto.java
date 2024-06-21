package dev.project.authserver.dto;

import lombok.Data;

@Data
public class RegisterDto {
    // Personal data
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    // Credentials
    private String username;
    private String password;
}
