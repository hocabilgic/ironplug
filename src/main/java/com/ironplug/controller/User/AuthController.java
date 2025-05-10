package com.ironplug.controller.User;


import com.ironplug.payload.request.user.UserUpdatePasswordRequest;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.service.user.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Yetkilendirme İşlemleri", description = "Şifre sıfırlama işlemleri için API'ler")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/resetPassword")
    @Operation(summary = "Şifre sıfırlama", description = "Şifre sıfırlama işlemini gerçekleştirir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Şifre başarıyla sıfırlandı",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek"),
            @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı")
    })
    public ResponseEntity<ResponseMessage<Void>> resetPassword(
            @Parameter(description = "Şifre sıfırlama bilgileri", required = true) @RequestBody @Valid UserUpdatePasswordRequest passwordResetRequest) {
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
