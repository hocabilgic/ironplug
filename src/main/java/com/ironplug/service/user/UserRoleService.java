package com.ironplug.service.user;


import com.ironplug.entity.enums.RoleType;
import com.ironplug.entity.user.UserRole;
import com.ironplug.exeption.ResourceNotFoundException;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.repository.User.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRole() {
        return  userRoleRepository.findAll();
    }
}
