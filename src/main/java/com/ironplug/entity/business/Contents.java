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
@Table(name = "t_content")
public class Contents {

    private static final String TIMEZONE = "Europe/Istanbul";
    private static final ZoneId ZONE_ID = ZoneId.of(TIMEZONE);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Başlık adı boş olamaz")
    @Size(min = 2, max = 255, message = "Başlık adı 3-255 karakter arasında olmalıdır")
    private String content_name;

    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private Title title; // User ile ilişki tanımlandı


    @Column(name = "create_at", nullable = false)
    @NotNull(message = "Create cannot be null")
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
