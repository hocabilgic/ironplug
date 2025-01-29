package com.ironplug.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
   private String first_name;
   private String last_name;
   private String phone;
   private String email;
   private String role;
}
