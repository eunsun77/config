package com.respark.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "respark.security")
public class ResparkSecurityProperties {

    /**
     * Cookie domain. Empty = host-only cookie (local).
     * Example prod: {@code .respark.shop}
     */
    private String cookieDomain = "";

    private boolean cookieSecure = false;

    /**
     * Lax for same-site local/dev. None requires Secure=true (HTTPS).
     */
    private String cookieSameSite = "Lax";

    private long accessTokenMaxAgeMinutes = 5;

    private long refreshTokenMaxAgeDays = 7;

    private List<String> allowedOrigins = new ArrayList<>(List.of(
            "http://localhost:3000",
            "http://localhost:3001"
    ));
}
