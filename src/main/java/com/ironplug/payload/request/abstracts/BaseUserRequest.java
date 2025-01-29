package com.ironplug.payload.request.abstracts;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseUserRequest extends AbstractUserRequest {

    @NotNull
    private String sifre;
}
