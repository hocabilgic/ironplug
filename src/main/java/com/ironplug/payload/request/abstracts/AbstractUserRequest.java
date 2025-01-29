package com.ironplug.payload.request.abstracts;


import com.ironplug.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class AbstractUserRequest {

    @NotNull(message = "Please enter your name")
    private String first_name;

    @NotNull(message = "Please enter your last name")
    private String last_name;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min=5, max=50 , message = "Your email should be between 5 and 50 chars")
    private String email;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Geçersiz telefon numarası formatı")
    private String telephone; // Telefon numarası (+90 555 555 5555 gibi)



    private UserRole userRole;










}
