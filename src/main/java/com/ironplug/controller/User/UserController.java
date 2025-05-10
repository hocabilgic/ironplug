package com.ironplug.controller.User;


import com.ironplug.payload.request.user.*;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.payload.response.user.AuthResponse;
import com.ironplug.payload.response.user.UserResponse;
import com.ironplug.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Kullanıcı Yönetimi", description = "Kullanıcı yönetimi API'leri (kayıt, giriş, şifre yenileme)")
public class UserController {

    private final UserService userServise;

    // F01 - login
    @PostMapping("/login") // http://localhost:8080/login
    @Operation(summary = "Kullanıcı girişi", description = "Kullanıcı kimlik doğrulama işlemi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Başarılı giriş",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası")
    })
    public ResponseEntity<AuthResponse> authenticateUser(
            @Parameter(description = "Giriş bilgileri", required = true) @RequestBody @Valid LoginRequest loginRequest) {
        return userServise.authenticateUser(loginRequest);
    }

    //F02 - register
    @PostMapping("/register") // http://localhost:8080/register
    @Operation(summary = "Kullanıcı kaydı", description = "Yeni kullanıcı kaydı oluşturma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kullanıcı başarıyla kaydedildi",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek")
    })
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(
            @Parameter(description = "Kullanıcı bilgileri", required = true) @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userServise.saveUser(userRequest));
    }

    //  F03 user arama
    
    // reset kod gönderme
    @PostMapping("/generate-reset-code")
    @Operation(summary = "Şifre sıfırlama kodu gönderme", description = "Kullanıcının e-postasına şifre sıfırlama kodu gönderir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sıfırlama kodu gönderildi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı"),
            @ApiResponse(responseCode = "500", description = "E-posta gönderme hatası")
    })
    public CompletableFuture<String> sendResetCode(
            @Parameter(description = "Şifre sıfırlama isteği", required = true) @RequestBody @Valid ResetCodeRequest resetCodeRequest) 
            throws MessagingException, IOException {
        return userServise.sendResetCode(resetCodeRequest);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Şifre sıfırlama", description = "Şifre sıfırlama kodunu kullanarak şifreyi yeniler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Şifre başarıyla sıfırlandı",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Geçersiz veya süresi dolmuş kod")
    })
    public CompletableFuture<String> resetPassword(
            @Parameter(description = "Şifre sıfırlama bilgileri", required = true) @RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        return userServise.resetPassword(passwordResetRequest);
    }

    @PutMapping("/updatePassword")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    @Operation(summary = "Şifre güncelleme", description = "Giriş yapmış kullanıcının şifresini günceller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Şifre başarıyla güncellendi",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Yetkilendirme hatası"),
            @ApiResponse(responseCode = "403", description = "Bu işlem için yetkiniz yok")
    })
    public CompletableFuture<String> updatePassword(
            @Parameter(description = "Şifre güncelleme bilgileri", required = true) @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
            HttpServletRequest httpServlet) {
        return userServise.updatePassword2(updatePasswordRequest, httpServlet);
    }
}
