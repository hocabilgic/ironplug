package com.ironplug.payload.response.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private Long userId;
    private Long controlId;
}
