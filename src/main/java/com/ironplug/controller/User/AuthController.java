package com.ironplug.controller.User;


import com.ironplug.payload.request.user.UserUpdatePasswordRequest;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.service.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reset")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseMessage<Void>> resetPassword(@RequestBody @Valid UserUpdatePasswordRequest passwordResetRequest) {
        ResponseMessage<Void> response = authService.resetPassword(passwordResetRequest);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }


    @PostMapping("/updatePassword")
    public ResponseEntity<ResponseMessage<Void>> updatePassword(@RequestBody @Valid UserUpdatePasswordRequest userUpdatePasswordRequest) {
        ResponseMessage<Void> response = authService.updatePassword(userUpdatePasswordRequest);
        return ResponseEntity.status(response.getHttpStatus()).body(response);

  //  @PostMapping("/update-password")
  //  public ResponseEntity<?> updatePassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
  //      // Kullanıcıyı email ve reset kodu ile doğrula
  //      Optional<User> optionalUser = userService
  //              .findByEmailAndResetCode(userUpdatePasswordRequest.getEmail()
  //                      ,userUpdatePasswordRequest.getReset_password_codee() );
  //      if (!optionalUser.isPresent()) {
  //          return ResponseEntity.badRequest().body("Geçersiz reset kodu veya e-posta!");
  //      }
//
  //      User user = optionalUser.get();
//
  //      // Şifreyi güncelle ve reset kodunu temizle
  //      userService.updatePassword(user, userUpdatePasswordRequest.getNewPassword());
//
  //      return ResponseEntity.ok("Şifreniz başarıyla güncellendi.");
//
    }
}
