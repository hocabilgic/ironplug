package com.ironplug.controller.User;


import com.ironplug.payload.request.user.LoginRequest;
import com.ironplug.payload.request.user.UserRequest;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.payload.response.user.AuthResponse;
import com.ironplug.payload.response.user.UserResponse;
import com.ironplug.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
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







}
