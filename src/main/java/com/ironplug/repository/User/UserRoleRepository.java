package com.ironplug.repository.User;


import com.ironplug.entity.enums.RoleType;
import com.ironplug.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByEnumRoleEquals(RoleType roleType);

}
