package com.ironplug.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PasswordResetRequest {

    @Size(min = 8, max = 120, message = "password must be between {min} and {max} characters")
   @NotNull(message = "new password cannot be null")
    private String newPassword;

    @Size(min = 8, max = 120, message = "password must be between {min} and {max} characters")
    @NotNull(message = "new password cannot be null")
    private String confirmPassword;

    @NotNull
    @Size(max = 8,min = 8)
    private String resetCode;

    @Column(name = "email", nullable = false, unique = true, length = 85)
    @Size(min = 2, max = 85, message = "email must be between {min} and {max} characters")
    @NotNull(message = "email cannot be null")
    @Email
    private String email;


}
