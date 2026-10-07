package com.farkhod.famousbooksapp.security.jwt;

import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.farkhod.famousbooksapp.security.jwt.service.AccessTokenJWTService;
import com.farkhod.famousbooksapp.security.jwt.service.RefreshTokenJWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTService {
    private final AccessTokenJWTService accessTokenJWTService;
    private final RefreshTokenJWTService refreshTokenJWTService;

    public String generateAccessToken(ProfileDto profileDto) {
        return accessTokenJWTService.generateToken(profileDto);
    }

    public String generateRefreshToken(ProfileDto profileDto) {
        return refreshTokenJWTService.generateToken(profileDto);
    }

    public ProfileDto getSubjectFromAccessToken(String token) {
        return accessTokenJWTService.getSubjectFromToken(token);
    }

    public ProfileDto getSubjectFromRefreshToken(String token) {
        return refreshTokenJWTService.getSubjectFromToken(token);
    }
}
