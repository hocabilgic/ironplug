package com.ironplug.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
//@Reatrable anatosyonu icin koyduk email gonderilirken hata olursa mail gondermeyi yeniden denesin istedim
public class RetryConfig {

}
