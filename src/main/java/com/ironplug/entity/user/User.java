package com.ironplug.entity.user;


import com.ironplug.entity.business.Title;
import com.ironplug.payload.messeges.ErrorMessages;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ad boş olmamalıdır!")
    @Size(min = 2, max = 30, message = "Ad en az 2 karakter olmalıdır!")
    private String firstName;

    @NotNull(message = "Soyad boş olmamalıdır!")
    @Size(min = 2, max = 30, message = "Soyad en az 2 karakter olmalıdır!")
    private String lastName;

    @NotNull(message = "E-posta boş olmamalıdır!")
    @Email(message = "Lütfen geçerli bir e-posta giriniz!")
    @Size(min = 10, max = 80, message = "E-postanız 10 ile 80 karakter arasında olmalıdır!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Şifre boş olmamalıdır!")
    private String sifre;

    @ManyToOne()
    private UserRole userRole;

    private Boolean built_in;

    private String telephone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> titles = new ArrayList<>();


    private static final String TIMEZONE = "Europe/Istanbul";
    private static final ZoneId ZONE_ID = ZoneId.of(TIMEZONE);

    private ZonedDateTime creatDate;
    private ZonedDateTime updateAt;

    @Builder.Default
    private Long point = 0L;

    private String resetPasswordCode;
    private ZonedDateTime resetPasswordCodeExpiry;

    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    //private int failedAttempts = 3; // Yanlış kod denemesi sayacı
    //private static final int MAX_FAILED_ATTEMPTS = 5; // Maksimum başarısız deneme hakkı

    //TODO bu kod baska clasta yazilabilir
    //TODO hatali reset codda mesaj donmeli ve hatali giris sayisinda sikinti var duzeltilmeli

    // 6 haneli rastgele alfanümerik kod üretir
    public void generateResetPasswordCode() {
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(possibleCharacters.charAt(RANDOM.nextInt(possibleCharacters.length())));
        }
        this.resetPasswordCode = codeBuilder.toString();
        this.resetPasswordCodeExpiry = ZonedDateTime.now().plusMinutes(3); // Kodun geçerlilik süresi
    }


}
