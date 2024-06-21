package dev.project.authserver.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ProfileDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
