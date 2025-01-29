package com.ironplug.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequest {
    private String password_hash;
    private String newPassword;
    private String email;

    private String reset_password_codee;
}
