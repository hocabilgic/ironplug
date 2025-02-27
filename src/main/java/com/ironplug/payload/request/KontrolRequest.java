package com.ironplug.payload.request;

import com.ironplug.entity.business.Title;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KontrolRequest {

    @NotNull(message = "kontrolEdildi alanı boş olamaz")
    private Boolean kontrolEdildi;

    @NotNull(message = "Başlık ID boş olamaz")
    private Long baslikId;

    private Long imageId;

    private Long titleId;

    private String contentName;
}
