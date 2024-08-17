package com.mod1;

import com.mod1.service.member.config.property.AuthProperty;
import com.mod1.service.member.config.property.JwtProperty;
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
public class Mod1Application {

    public static void main(String[] args) {
        SpringApplication.run(Mod1Application.class, args);
    }

}
