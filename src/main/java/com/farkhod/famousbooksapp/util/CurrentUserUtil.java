package com.farkhod.famousbooksapp.util;

import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@UtilityClass
public class CurrentUserUtil {

    @NotNull
    public ProfileDto currentUser() {
        return (ProfileDto) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

}
