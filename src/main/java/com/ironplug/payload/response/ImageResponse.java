package com.ironplug.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private Long id;
    private String imageUrl;
    private LocalDateTime createDate;
    private Long titleId;
    private String description;
    private Boolean isActive;
} 