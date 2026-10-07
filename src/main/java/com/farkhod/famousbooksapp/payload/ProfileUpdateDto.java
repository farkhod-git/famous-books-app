package com.farkhod.famousbooksapp.payload;

import java.time.LocalDate;
import java.util.UUID;

public record ProfileUpdateDto(String firstname,
                               String lastname,
                               LocalDate birthdate,
                               String address,
                               UUID avatarId) {
}
