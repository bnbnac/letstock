package com.letstock;

import com.letstock.service.member.config.property.AuthProperty;
import com.letstock.service.member.config.property.JwtProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties({
        AuthProperty.class,
        JwtProperty.class
})
@EnableScheduling
@SpringBootApplication
public class LetstockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetstockApplication.class, args);
    }

}
