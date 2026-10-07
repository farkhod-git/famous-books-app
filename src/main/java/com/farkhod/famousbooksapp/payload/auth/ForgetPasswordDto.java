package com.farkhod.famousbooksapp.payload.auth;

import com.farkhod.famousbooksapp.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgetPasswordDto(@NotBlank(message = "Email is required")
                                @Email(regexp = AppConstants.EMAIL_REGEXP) String email) {
}
