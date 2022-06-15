package com.hideyoshi.hideyoshiportfolio.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {

    public static Integer TOKEN_DURATION;

    public static String TOKEN_SECRET;

    @Value("${com.hideyoshi.token.duration}")
    public void setTokenDuration(Integer tokenDuration) {
        TOKEN_DURATION = tokenDuration;
    }

    @Value("${com.hideyoshi.token.secret}")
    public void setTokenSecret(String tokenSecret) {
        TOKEN_SECRET = tokenSecret;
    }

}
