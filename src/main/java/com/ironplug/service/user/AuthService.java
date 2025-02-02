package com.ironplug.service.user;


import com.ironplug.entity.user.User;
import com.ironplug.exeption.ResourceNotFoundException;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.messeges.SuccessMessages;
import com.ironplug.payload.request.user.UserUpdatePasswordRequest;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.repository.User.UserRepository;
import com.ironplug.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;

    public ResponseMessage<Void> resetPassword(UserUpdatePasswordRequest passwordResetRequest) {
        // Kullanıcının olup olmadığını kontrol et ve genel mesaj döndür
        Optional<User> userOptional = userRepository.findByEmail(passwordResetRequest.getEmail());
        if (!userOptional.isPresent()) {
            return new ResponseMessage<>(null, HttpStatus.OK, SuccessMessages.MAIL_HATLI);
        }

        User user = userOptional.get();
        generateResetPasswordCode(user);
        // Kullanıcı adını ve soyadını da geçerek e-posta gönder
        try {
            emailService.sendResetCode(user.getEmail()
                    , user.getFirstName()
                    , user.getLastName()
                    , user.getResetPasswordCode()
            , user.getResetPasswordCodeExpiry().toLocalDateTime());



        } catch (MessagingException e) {
            e.printStackTrace(); // Hata mesajını detaylı logla
            return new ResponseMessage<>(null, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.EMAIL_CANNOT_BE_NULL);
        }


        return new ResponseMessage<>(null, HttpStatus.OK, SuccessMessages.MAIL_GONDERILDI);
    }

    public ResponseMessage<Void> updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest) {
        User user = userRepository.findByEmailAndResetPasswordCode(userUpdatePasswordRequest.getEmail(), userUpdatePasswordRequest.getReset_password_codee())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email or reset code", userUpdatePasswordRequest.getEmail()));

        // Şifreyi güncelle ve reset kodunu temizle
      //  userService.updatePassword(user, userUpdatePasswordRequest.getNewPassword());

        return new ResponseMessage<>(null, HttpStatus.OK, SuccessMessages.PASSWORD_UPDATE);
    }

    // Şifre sıfırlama kodunu oluştur ve kaydet
    private void generateResetPasswordCode(User user) {
        user.generateResetPasswordCode();
        userRepository.save(user);
    }
}
