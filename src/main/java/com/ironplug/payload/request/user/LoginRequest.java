package com.ironplug.payload.request.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Email must not be empty")
    private String email;

    @NotNull(message = "Password must not be empty")
    private String sifre;

}
