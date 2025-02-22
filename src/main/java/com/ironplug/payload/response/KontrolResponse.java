package com.ironplug.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

    @Getter
    @Setter
    public class KontrolResponse {

        private Long id;
        private Boolean kontrolEdildi;
        private String baslikAdi;
        private String contentName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Istanbul")
        private ZonedDateTime createAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Europe/Istanbul")
        private ZonedDateTime updateAt;
    }



