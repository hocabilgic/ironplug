package com.ironplug.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.ironplug.entity.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_title")
public class Title {


    private static final String TIMEZONE = "Europe/Istanbul";
    private static final ZoneId ZONE_ID = ZoneId.of(TIMEZONE);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Başlık adı boş olamaz")
    @Size(min = 2, max = 255, message = "Başlık adı 2-255 karakter arasında olmalıdır")
    private String title_name;

    private Boolean isControl=false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User ile ilişki tanımlandı


    @Column(name = "create_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = TIMEZONE)
    private ZonedDateTime createAt;

    @Column(name = "update_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = TIMEZONE)
    private ZonedDateTime updateAt;


    @PrePersist
    private void onCreate() {
        ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
        createAt = now;

    }



}
