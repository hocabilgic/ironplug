package com.ironplug.payload.response.business;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentsResponse {

    private Long id;
    private String content_name;
}
