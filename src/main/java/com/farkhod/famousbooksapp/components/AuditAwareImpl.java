package com.farkhod.famousbooksapp.components;

import com.farkhod.famousbooksapp.entities.User;
import com.farkhod.famousbooksapp.mappers.UserMapper;
import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.farkhod.famousbooksapp.repositories.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<User> {
    private final UserRepository userRepository;

    @Override
    public @NonNull Optional<User> getCurrentAuditor() {
        ProfileDto profileDto = (ProfileDto) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        assert profileDto != null;
        return Optional.of(userRepository.findById(profileDto.getId()).orElseThrow());
    }
}
