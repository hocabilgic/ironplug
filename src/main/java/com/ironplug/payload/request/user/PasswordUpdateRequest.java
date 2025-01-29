package com.ironplug.payload.request.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PasswordUpdateRequest {
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit"),
            @Pattern(regexp = ".*[a-zA-Z].*", message = "Password must contain at least one letter"),
            @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character")
    })
    private String current_password;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit"),
            @Pattern(regexp = ".*[a-zA-Z].*", message = "Password must contain at least one letter"),
            @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character")
    })
    private String new_password;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit"),
            @Pattern(regexp = ".*[a-zA-Z].*", message = "Password must contain at least one letter"),
            @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character")
    })
    private String retry_new_password;
}
