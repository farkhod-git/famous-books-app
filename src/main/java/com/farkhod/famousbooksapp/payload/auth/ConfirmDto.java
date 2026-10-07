package com.farkhod.famousbooksapp.payload.auth;

import com.farkhod.famousbooksapp.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ConfirmDto(@NotBlank(message = "Email cannot be blank")
                         @Email(regexp = AppConstants.EMAIL_REGEXP, message = "Invalid email format")
                         String email,

                         @NotBlank(message = "Code cannot be blank")
                         String code) {
}
