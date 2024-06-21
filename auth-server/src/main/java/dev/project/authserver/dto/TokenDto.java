package dev.project.authserver.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TokenDto {
    private boolean status;
    private String token;
}
