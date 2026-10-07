package com.farkhod.famousbooksapp.security.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.refresh-token")
public class JWTRefreshTokenProperties extends JwtProperties {
}
