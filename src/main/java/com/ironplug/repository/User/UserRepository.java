package com.ironplug.repository.User;


import com.ironplug.entity.enums.RoleType;
import com.ironplug.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Email ile kullanıcıyı bul
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    // Email'in var olup olmadığını kontrol et
    boolean existsByEmail(String email);

    // Belirli bir RoleType için kullanıcı sayısını bul (Admin sayısı için)
    @Query("SELECT COUNT(u) FROM User u INNER JOIN u.userRole r WHERE r.roleType = :roleType")
    long countAdmin(@Param("roleType") RoleType roleType);

    // Kullanıcı arama (isim, soyisim, email, telefon, doğum yeri, uyruk, baba adı, anne adı)


    // Email ve reset şifre kodu ile kullanıcıyı bul
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.resetPasswordCode = :resetCode")
    Optional<User> findByEmailAndResetPasswordCode(@Param("email") String email, @Param("resetCode") String resetCode);

}
