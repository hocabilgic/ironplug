package com.ironplug.payload.request.busines;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String description;

    //@NotNull(message = "Image data cannot be null")
    private byte[] data;

    //@NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Control ID cannot be null")
    private Long controlId;


}
