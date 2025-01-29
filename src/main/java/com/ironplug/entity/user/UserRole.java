package com.ironplug.entity.user;


import com.ironplug.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType roleType;

    @NotNull
    private String name;
}
