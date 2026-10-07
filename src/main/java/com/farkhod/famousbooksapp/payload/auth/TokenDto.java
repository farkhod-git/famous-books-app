package com.farkhod.famousbooksapp.payload.auth;

public record TokenDto(String accessToken,
                       String refreshToken) {
}
