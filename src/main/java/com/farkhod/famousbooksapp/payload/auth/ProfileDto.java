package com.farkhod.famousbooksapp.payload.auth;

import com.farkhod.famousbooksapp.constants.AppConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDto {
    UUID id;
    String firstname;
    String lastname;
    String email;
    @JsonFormat(pattern = AppConstants.DATE_FORMAT)
    LocalDate birthdate;
    String address;
    UUID avatarId;
}
