package com.ironplug.service.mail;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;


@Component
public class GenerateResetCode {

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateResetCode() {
        int number = 10000000 + secureRandom.nextInt(90000000);
        return String.valueOf(number);
    }
}



