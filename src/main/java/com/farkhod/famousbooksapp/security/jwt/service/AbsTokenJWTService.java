package com.farkhod.famousbooksapp.security.jwt.service;

import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.farkhod.famousbooksapp.security.jwt.properties.JwtProperties;
import com.farkhod.famousbooksapp.util.MyJWTUtil;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public abstract class AbsTokenJWTService {
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public String generateToken(ProfileDto profileDto) {
        return MyJWTUtil.generateToken(objectMapper.writeValueAsString(profileDto), jwtProperties.getExpiry(), jwtProperties.getSecretKey());
    }

    public ProfileDto getSubjectFromToken(String token) {
        return objectMapper.readValue(MyJWTUtil.getSubjectFromToken(token, jwtProperties.getSecretKey()), ProfileDto.class);
    }
}
