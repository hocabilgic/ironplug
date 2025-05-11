package com.ironplug.controller.User;


import com.ironplug.payload.request.user.*;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.payload.response.user.AuthResponse;
import com.ironplug.payload.response.user.UserResponse;
import com.ironplug.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userServise;

    // F01 - login
    @PostMapping("/login") // http://localhost:8080/login

    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return userServise.authenticateUser(loginRequest);
    }

    //F02 - register
    @PostMapping("/register") // http://localhost:8080/register
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userServise.saveUser(userRequest));
    }


    //  F03 user arama



    // reset kod gönderme
    @PostMapping("/generate-reset-code")
    public CompletableFuture<String> sendResetCode(@RequestBody @Valid ResetCodeRequest resetCodeRequest)
            throws MessagingException, IOException {
        return userServise.sendResetCode(resetCodeRequest);
    }


    @PostMapping("/reset-password")
    public CompletableFuture<ResponseEntity<String>> resetPassword(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        return userServise.resetPassword(passwordResetRequest)
                .thenApply(ResponseEntity::ok);
    }


    @PutMapping("/updatePassword")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public String updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
                                 HttpServletRequest httpServlet) {

        return userServise.updatePassword2(updatePasswordRequest, httpServlet);
    }






}
