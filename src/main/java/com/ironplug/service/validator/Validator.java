package com.ironplug.service.validator;


import com.ironplug.entity.user.User;
import com.ironplug.exeption.BadRequestException;
import com.ironplug.exeption.NotMatched;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final UserRepository userRepository;








    public void validateStrongPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_LENGTH);
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (!hasLetter) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_DONT_CONSIST_LETTER);
        }
        if (!hasDigit) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_DONT_CONSIST_DIGIT);
        }
        if (!hasSpecialChar) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_DONT_CONSIST_SPECIAL_CHAR);
        }
    }

    public void validateEmailIsUnique(String email) {
        if (userRepository.existsByEmailEquals(email)) {
            throw new IllegalArgumentException(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL);
        }
    }

    public void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_NOT_MATCH);
        }
    }

    public void validateBuiltInUser(User user) {
        if (Boolean.TRUE.equals(user.getBuilt_in())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    public void validateResetCode(String resetCodeFromRequest, String resetCodeFromUser) {
        if (!resetCodeFromRequest.equals(resetCodeFromUser)) {
            throw new NotMatched(ErrorMessages.RESET_CODE_NOT_MATCH);
        }
    }


    }



