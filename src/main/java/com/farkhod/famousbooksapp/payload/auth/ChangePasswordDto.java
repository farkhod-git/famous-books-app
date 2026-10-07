package com.farkhod.famousbooksapp.payload.auth;

import com.farkhod.famousbooksapp.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDto(@NotBlank(message = "Email is required")
                                @Email(regexp = AppConstants.EMAIL_REGEXP, message = "Email is invalid")
                                String email,
                                @NotBlank(message = "Password is required")
                                String newPassword,
                                @NotBlank(message = "Pre-password is required")
                                String newPrePassword,
                                @NotBlank(message = "Code is required")
                                String code) {
}
