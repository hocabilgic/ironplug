package com.ironplug.payload.request.busines;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TitleRequest {

    @Column(nullable = false, length = 255)
    private String title_name;




}
