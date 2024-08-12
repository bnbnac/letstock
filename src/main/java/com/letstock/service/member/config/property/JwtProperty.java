package com.letstock.service.member.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {

    private byte[] key;

    @Setter
    private Long maxAgeInMinutes;

    public void setKey(String key) {
        this.key = Base64.getDecoder().decode(key);
    }

}