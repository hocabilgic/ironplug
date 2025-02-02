package com.ironplug.payload.request.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePasswordRequest {

    @Size(min = 8, max = 120, message = "password must be between {min} and {max} characters")
    @NotNull(message = "password cannot be null")
    private String oldPassword;


    @Size(min = 8, max = 120, message = "New password must be between {min} and {max} characters")
    @NotNull(message = "New Password cannot be null")
    private String newPassword;

    @Size(min = 8, max = 120, message = "password must be between {min} and {max} characters")
    @NotNull(message = "ConfirmPassword cannot be null")
    private String confirmPassword;
}
