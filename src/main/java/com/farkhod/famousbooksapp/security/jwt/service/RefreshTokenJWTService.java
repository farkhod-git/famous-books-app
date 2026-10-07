package com.farkhod.famousbooksapp.security.jwt.service;


import com.farkhod.famousbooksapp.security.jwt.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RefreshTokenJWTService extends AbsTokenJWTService {
    public RefreshTokenJWTService(@Qualifier("JWTRefreshTokenProperties") JwtProperties jwtProperties,
                                  ObjectMapper objectMapper) {
        super(jwtProperties, objectMapper);
    }
}
