package com.ironplug.payload.request.busines;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TitleRequest {

    @Column(nullable = false)
    @NotBlank(message = "Başlık adı boş olamaz")
    @Size(min = 2, max = 255, message = "Başlık adı 2-255 karakter arasında olmalıdır")
    private String title_name;




}
