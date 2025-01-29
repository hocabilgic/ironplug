package com.ironplug.security.service;


import com.ironplug.entity.user.User;
import com.ironplug.repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //******************
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User bilgileri bulunamadı"));

        if (user != null){
            return new UserDetailsImpl(
                    user.getId(),

                    //**********************
                    user.getSifre(),

                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),

                    //**********
                    user.getUserRole().getRoleType().name()

                    );
        }throw new UsernameNotFoundException("User : "+ email + "not found");

    }
}
