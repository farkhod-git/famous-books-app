package com.farkhod.famousbooksapp.payload.auth;

import com.farkhod.famousbooksapp.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginDto(@NotBlank
                       @Email(regexp = AppConstants.EMAIL_REGEXP, message = "Invalid email format")
                       String email,
                       @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
                       @NotBlank
                       String password) {
}
