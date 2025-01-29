package com.ironplug.payload.helpers;



import com.ironplug.entity.user.User;
import com.ironplug.exeption.ResourceNotFoundException;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.repository.User.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class MethodHelper {
    private final UserRepository userRepository;

    public MethodHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }
}
