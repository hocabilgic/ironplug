package com.ironplug.service.validator;

import com.ironplug.entity.user.User;
import com.ironplug.exeption.ConflictException;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.request.abstracts.AbstractUserRequest;
import com.ironplug.payload.request.user.UpdateUserRequest;
import com.ironplug.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {
    private final UserRepository userRepository;

    public void checkUniqueProperties(User user, AbstractUserRequest baseUserRequest) {
        String updatedEmail = baseUserRequest.getEmail();
        boolean isChanged = false;

        // Kullanıcının mevcut e-posta adresiyle güncellenmiş e-posta adresini karşılaştır
        if (!user.getEmail().equalsIgnoreCase(updatedEmail)) {
            isChanged = true;
        }

        if (isChanged) {
            checkDuplicate(updatedEmail);
        }
    }

    public void checkUniquePropertiess(User user, UpdateUserRequest baseUserRequest) {
        String updatedEmail = baseUserRequest.getEmail();
        boolean isChanged = false;

        // Kullanıcının mevcut e-posta adresiyle güncellenmiş e-posta adresini karşılaştır
        if (!user.getEmail().equalsIgnoreCase(updatedEmail)) {
            isChanged = true;
        }

        if (isChanged) {
            checkDuplicate(updatedEmail);
        }
    }

    public void checkDuplicate(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }
    }
}
