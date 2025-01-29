package com.ironplug.payload.mapper;

import com.ironplug.entity.user.User;
import com.ironplug.payload.request.abstracts.BaseUserRequest;
import com.ironplug.payload.response.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .first_name(user.getFirstName()) // İsim validasyonlu
                .last_name(user.getLastName())   // Soyisim validasyonlu
                .email(user.getEmail())          // Email validasyonlu
                .create_at(user.getCreatDate())   // Oluşturulma zamanı
                .update_at(user.getUpdateAt())   // Güncellenme zamanı
                .role(user.getUserRole().getName()) // Kullanıcı rolü
                .telephone(user.getTelephone())  // Telefon numarası validasyonlu

                .build();
    }


    public User mapUserRequestToUser(BaseUserRequest userRequest) {

        return User.builder()

                .firstName(userRequest.getFirst_name())    // İsim
                .lastName(userRequest.getLast_name())      // Soyisim
                .sifre(userRequest.getSifre())             // Şifre
                .email(userRequest.getEmail())             // Email
                .telephone(userRequest.getTelephone())     // Telefon numarası
                .build();

    }
}