package com.ironplug.payload.request.busines;


import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ContentsRequest {

    @Column(nullable = false, length = 255)
    private String content_name;
}
