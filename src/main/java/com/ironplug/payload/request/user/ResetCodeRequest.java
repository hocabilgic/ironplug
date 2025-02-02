package com.ironplug.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetCodeRequest {

    @Size(min = 10, max = 80, message = "email must be between {min} and {max} characters")
    @NotNull(message = "email cannot be null")
    @Email
    private String email;


}
