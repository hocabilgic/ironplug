package com.ironplug.payload.response.abstracts;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;

    private ZonedDateTime create_at;
    private ZonedDateTime update_at;
    private String role;

    private String telephone; // Telefon numarası (+90 555 555 5555 gibi)








}
