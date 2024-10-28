package com.Saransh.E_Commerce.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Invalid Credentials")
    private String email;

    @NotBlank(message = "Invalid Credentials")
    private String password;
}
