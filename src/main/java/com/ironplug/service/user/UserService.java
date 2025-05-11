package com.ironplug.service.user;


import com.ironplug.entity.enums.RoleType;
import com.ironplug.entity.user.User;
import com.ironplug.entity.user.UserRole;
import com.ironplug.payload.helpers.MethodHelper;
import com.ironplug.payload.mapper.UserMapper;
import com.ironplug.payload.messeges.ErrorMessages;
import com.ironplug.payload.messeges.SuccessMessages;
import com.ironplug.payload.request.user.*;
import com.ironplug.payload.response.ResponseMessage;
import com.ironplug.payload.response.user.AuthResponse;
import com.ironplug.payload.response.user.UserResponse;
import com.ironplug.repository.User.UserRepository;

import com.ironplug.security.jwt.JwtUtils;
import com.ironplug.security.service.UserDetailsImpl;
import com.ironplug.service.mail.EmailService;
import com.ironplug.service.mail.GenerateResetCode;
import com.ironplug.service.validator.UniquePropertyValidator;
import com.ironplug.service.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;
        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;
        private final UserRoleService userRoleService;
        private final UserMapper userMapper;
        private final UniquePropertyValidator uniquePropertyValidator;
        private final MethodHelper methodHelper;
        private final GenerateResetCode resetCode;
        private final EmailService emailService;
        private final Validator validator;
        private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // BCryptPasswordEncoder kullanımı

        // F01 - login
        public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
                String email = loginRequest.getEmail();
                String password = loginRequest.getSifre();

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String token = "Bearer " + jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                Set<String> roles = userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

                Optional<String> role = roles.stream().findFirst();

                AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
                authResponse.email(userDetails.getEmail());
                authResponse.first_name(userDetails.getFirstName());

                authResponse.token(token.substring(7));

                role.ifPresent(authResponse::role);
                return ResponseEntity.ok(authResponse.build());
        }

        // F02 - register
        public ResponseMessage<UserResponse> saveUser(UserRequest userRequest) {
                uniquePropertyValidator.checkDuplicate(userRequest.getEmail());

                Set<UserRole> userRole = new HashSet<>();
                UserRole customer = userRoleService.getUserRole(RoleType.CUSTOMER);
                userRole.add(customer);

                User user = userMapper.mapUserRequestToUser(userRequest);
                user.setBuilt_in(Boolean.FALSE);
                user.setUserRole(customer);
                user.setSifre(passwordEncoder.encode(user.getSifre())); // Şifre hashleniyor

                User savedUser = userRepository.save(user);

                return ResponseMessage.<UserResponse>builder()
                        .message(SuccessMessages.USER_CREATED)
                        .object(userMapper.mapUserToUserResponse(savedUser))
                        .build();
        }

        public long countAllAdmins(){
                return userRepository.countAdmin(RoleType.ADMIN);
        }

        public String saveAdmin(UserRequest userRequest) {
                Set<UserRole> userRole = new HashSet<>();
                UserRole admin = userRoleService.getUserRole(RoleType.ADMIN);
                userRole.add(admin);

                User user = userMapper.mapUserRequestToUser(userRequest);
                user.setBuilt_in(Boolean.TRUE);
                user.setSifre(passwordEncoder.encode(userRequest.getSifre())); // Şifre hashleniyor
                user.setUserRole(admin);
                userRepository.save(user);

                return SuccessMessages.USER_CREATED;
        }





        // Email ile kullanıcıyı bul
        public Optional<User> findByEmail(String email) {
                return userRepository.findByEmail(email);
        }

        public Optional<User> findByEmailAndResetCode(String email, String resetCode) {
                return userRepository.findByEmailAndResetPasswordCode(email, resetCode);
        }



        @Async
        public CompletableFuture<String> sendResetCode(ResetCodeRequest resetCodeRequest) throws MessagingException, IOException {

                String mail = resetCodeRequest.getEmail();

                User user = methodHelper.findUserByEmail(mail);



                String code = resetCode.generateResetCode();

                // Asenkron e-posta gönderimi
                emailService.resetCodeEmail(
                        code,
                        user.getEmail(),
                        user.getLastName(),
                        user.getFirstName()
                );

                user.setResetPasswordCode(code);
                userRepository.save(user);

                return CompletableFuture.completedFuture(SuccessMessages.FORGOT_PASSWORD_RESET_CODE_EMAIL_SEND);
        }



        @Async
        public CompletableFuture<String> resetPassword(PasswordResetRequest passwordResetRequest) {

                validator.validatePasswordMatch(passwordResetRequest.getConfirmPassword(), passwordResetRequest.getNewPassword());

                String resetCodeFromRequest = passwordResetRequest.getResetCode();
                User user = methodHelper.findUserByEmail(passwordResetRequest.getEmail());

                validator.validateBuiltInUser(user);
                validator.validateResetCode(resetCodeFromRequest, user.getResetPasswordCode());
               // validator.validateStrongPassword(passwordResetRequest.getNewPassword());

                user.setSifre(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
                user.setResetPasswordCode(null);
                userRepository.save(user);

                return CompletableFuture.completedFuture(SuccessMessages.CHANGED_PASSWORD);
        }






        public String updatePassword2(UpdatePasswordRequest updatePasswordRequest, HttpServletRequest httpServlet) {

                validator.validatePasswordMatch(updatePasswordRequest.getNewPassword(), updatePasswordRequest.getConfirmPassword());
               // validator.validateStrongPassword(updatePasswordRequest.getNewPassword());

                String email = (String) httpServlet.getAttribute("email");
                User user = methodHelper.findUserByEmail(email);

                if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getSifre())) {
                        throw new IllegalStateException(ErrorMessages.OLD_PASSWORD_NOT_MATCH);
                }

                user.setSifre(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
                userRepository.save(user);

                return SuccessMessages.PASSWORD_UPDATED;
        }



}
