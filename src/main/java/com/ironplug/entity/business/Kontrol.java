package com.ironplug.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_kontrol")
public class Kontrol {
    private static final String TIMEZONE = "Europe/Istanbul";
    private static final ZoneId ZONE_ID = ZoneId.of(TIMEZONE);


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kontrol_edildi")
    private Boolean kontrolEdildi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id")
    private Title baslik;


    private String contentName;



    @OneToOne(mappedBy = "kontrol", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image images;

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
        updateAt = now;

    }

    @PreUpdate
    public void onUpdate() {
        ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
        updateAt = now;
    }
} 