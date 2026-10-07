package com.farkhod.famousbooksapp.security.jwt;

import com.farkhod.famousbooksapp.enums.TokenTypeEnum;
import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // any exception reasons to return back request by spring security
        try {
            String token = token(request);

            ProfileDto profileDto = jwtService.getSubjectFromAccessToken(token);

            var authToken = new UsernamePasswordAuthenticationToken(profileDto, null, Collections.emptyList());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception _) {
        }

        filterChain.doFilter(request, response);
    }

    private static String token(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
