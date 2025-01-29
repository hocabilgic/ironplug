package com.ironplug.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String email;

    @JsonIgnore
    private String sifre;

    private String firstName; //User Entity first_name yazilmis camelCase ile yazildi

    private String last_name;

    private String userRole;



    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,String sifre,String email, String firstName, String last_name, String userRole) {
        this.id = id;
        this.sifre=sifre;
        this.email = email;

        this.firstName = firstName;
        this.last_name = last_name;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userRole));
        this.authorities = grantedAuthorities;
    }



    //bunu silmeyin pls
    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return sifre;
    }

    //!!!username field yok ama email yazinda override bozuluyor HELP!!!
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id,user.getId());
    }


}
