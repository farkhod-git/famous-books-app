package com.farkhod.famousbooksapp.security.oauth2;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.entities.User;
import com.farkhod.famousbooksapp.mappers.UserMapper;
import com.farkhod.famousbooksapp.payload.attachment.ByteArrayAttachmentDto;
import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.farkhod.famousbooksapp.repositories.UserRepository;
import com.farkhod.famousbooksapp.security.jwt.JWTService;
import com.farkhod.famousbooksapp.service.AttachmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

// http://localhost:8080/api/login/oauth2/code/google
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final AttachmentService attachmentService;
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) Objects.requireNonNull(authentication.getPrincipal());

        String email = Objects.requireNonNull(oAuth2User.getAttribute("email"));

        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setPassword(UUID.randomUUID().toString());
            user.setFirstname(oAuth2User.getAttribute("given_name"));
            user.setLastname(oAuth2User.getAttribute("family_name"));
        }

        user.setEnabled(true);
        userRepository.save(user);

        if (user.getAvatar() == null)
            saveProfilePicture(oAuth2User, user);

        ProfileDto profileDto = userMapper.toProfileDto(user);

        // create token
        String accessToken = jwtService.generateAccessToken(profileDto);
        String refreshToken = jwtService.generateRefreshToken(profileDto);

        response.sendRedirect("http://localhost:5173/oauth2-success?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }

    private void saveProfilePicture(OAuth2User oAuth2User, User user) {
        try {
            // save profile
            String picture = oAuth2User.getAttribute("picture");
            if (picture != null) {
                try (HttpClient client = HttpClient.newHttpClient()) {
                    HttpRequest req = HttpRequest.newBuilder()
                            .uri(URI.create(picture))
                            .build();

                    HttpResponse<InputStream> resp = client.send(req, HttpResponse.BodyHandlers.ofInputStream());

                    try (InputStream is = resp.body()) {
                        ByteArrayAttachmentDto byteArrayAttachmentDto = new ByteArrayAttachmentDto();
                        byteArrayAttachmentDto.setContent(is.readAllBytes());
                        byteArrayAttachmentDto.setFilename(UUID.randomUUID() + "-avatar.jpg");
                        byteArrayAttachmentDto.setContentType("image/jpeg");
                        byteArrayAttachmentDto.setExtension("jpg");
                        byteArrayAttachmentDto.setUser(user);
                        Attachment avatar = attachmentService.save(byteArrayAttachmentDto);
                        user.setAvatar(avatar);
                        userRepository.save(user);
                    }
                }
            }
        } catch (Exception _) {
        }
    }
}
