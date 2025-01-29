package com.ironplug.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AuthResponse {
    private String first_name;

    private String last_name;

    private String email;

    private String role;

    private String token;
}