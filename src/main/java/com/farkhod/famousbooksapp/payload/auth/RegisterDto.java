package com.farkhod.famousbooksapp.payload.auth;

import com.farkhod.famousbooksapp.constants.AppConstants;
import com.farkhod.famousbooksapp.entities.Attachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto {
    @NotBlank(message = "Firstname cannot be blank")
    String firstname;

    String lastname;

    @NotBlank(message = "Email cannot be blank")
    @Email(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$", message = "Email is not valid")
    String email;

    @NotBlank(message = "Password cannot be blank")
    @Length(min = 6, max = 20, message = "Password must be at least 6 and at most 20 characters long")
    String password;

    @NotBlank(message = "Pre Password cannot be blank")
    @Length(min = 6, max = 20, message = "Pre Password must be at least 6 and at most 20 characters long")
    String prePassword;

    @JsonFormat(pattern = AppConstants.DATE_FORMAT)
    LocalDate birthdate;

    String address;

    UUID avatarId;
}
