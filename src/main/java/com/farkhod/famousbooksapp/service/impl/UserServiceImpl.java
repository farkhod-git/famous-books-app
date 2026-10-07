package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.entities.User;
import com.farkhod.famousbooksapp.exceptions.MyBadRequestException;
import com.farkhod.famousbooksapp.exceptions.MyConflictException;
import com.farkhod.famousbooksapp.mappers.UserMapper;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.ProfileUpdateDto;
import com.farkhod.famousbooksapp.payload.auth.*;
import com.farkhod.famousbooksapp.repositories.UserRepository;
import com.farkhod.famousbooksapp.security.jwt.JWTService;
import com.farkhod.famousbooksapp.service.AttachmentService;
import com.farkhod.famousbooksapp.service.ConfirmationService;
import com.farkhod.famousbooksapp.service.StringValueRedisService;
import com.farkhod.famousbooksapp.service.UserService;
import com.farkhod.famousbooksapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int CODE_EXPIRY_TIME_IN_SECONDS = 120;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ConfirmationService confirmationService;
    private final StringValueRedisService stringValueRedisService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AttachmentService attachmentService;

    @Override
    public ApiResponseDto<ProfileDto> register(RegisterDto registerDto) {
        if (!Objects.equals(registerDto.getPassword(), registerDto.getPrePassword())) {
            throw new MyBadRequestException("Passwords do not match");
        }

        User user = userRepository.findByEmail(registerDto.getEmail());

        if (user != null) {
            if (user.isEnabled())
                throw new MyConflictException("User already exists");

            userMapper.update(registerDto, user);
        } else {
            user = userMapper.toEntity(registerDto);
        }

        // set avatar
        if (registerDto.getAvatarId() != null) {
            Attachment avatar = attachmentService.getById(registerDto.getAvatarId());
            user.setAvatar(avatar);
        }

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        sendConfirmationCode(user, "confirmation:code:");

        return ApiResponseDto.success(userMapper.toProfileDto(user));
    }

    private void sendConfirmationCode(User user, String baseKey) {
        char[] code = new char[4];
        for (int i = 0; i < code.length; i++)
            code[i] = (char) (Math.random() * 10 + '0');

        String codeValue = String.valueOf(code);

        stringValueRedisService.save(baseKey + user.getId().toString(), codeValue, CODE_EXPIRY_TIME_IN_SECONDS);
        confirmationService.sendConfirmation(user.getEmail(), codeValue);
    }

    @Override
    public ApiResponseDto<TokenDto> confirm(ConfirmDto confirmDto) {
        User user = userRepository.findByEmail(confirmDto.email());
        if (user == null || user.isEnabled())
            throw new MyBadRequestException("User not found to confirm");

        checkConfirmationCode(user, confirmDto.code(), "confirmation:code:");

        user.setEnabled(true);
        userRepository.save(user);

        ProfileDto profileDto = userMapper.toProfileDto(user);

        return ApiResponseDto.success(generateTokenDto(profileDto));
    }

    private void checkConfirmationCode(User user, String confirmationCode, String baseKey) {
        String code = stringValueRedisService.get(baseKey + user.getId().toString());
        if (!Objects.equals(confirmationCode, code))
            throw new MyBadRequestException("Invalid confirmation code");
    }

    @Override
    public ApiResponseDto<TokenDto> login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );

        User user = (User) Objects.requireNonNull(authenticate.getPrincipal());

        ProfileDto profileDto = userMapper.toProfileDto(user);

        return ApiResponseDto.success(generateTokenDto(profileDto));
    }

    private TokenDto generateTokenDto(ProfileDto profileDto) {
        return new TokenDto(jwtService.generateAccessToken(profileDto), jwtService.generateRefreshToken(profileDto));
    }

    @Override
    public ApiResponseDto<ProfileDto> getMe() {
        return ApiResponseDto.success(CurrentUserUtil.currentUser());
    }

    @Override
    public ApiResponseDto<ProfileDto> updateMe(ProfileUpdateDto profileUpdateDto) {
        ProfileDto profileDto = CurrentUserUtil.currentUser();
        User user = userRepository.findById(profileDto.getId()).orElseThrow();

        if (profileUpdateDto.avatarId() != null) {
            Attachment avatar = attachmentService.getById(profileUpdateDto.avatarId());
            user.setAvatar(avatar);
        }

        userMapper.updateUser(profileUpdateDto, user);

        userRepository.save(user);

        return ApiResponseDto.success(userMapper.toProfileDto(user));
    }

    @Override
    public ApiResponseDto<Object> forgetPassword(ForgetPasswordDto forgetPasswordDto) {
        User user = getUser(forgetPasswordDto.email());

        sendConfirmationCode(user, "forget:password:");

        return ApiResponseDto.success(null);
    }

    @Override
    public ApiResponseDto<ProfileDto> changePassword(ChangePasswordDto changePasswordDto) {
        if (!Objects.equals(changePasswordDto.newPassword(), changePasswordDto.newPrePassword())) {
            throw new MyBadRequestException("Passwords do not match");
        }

        User user = getUser(changePasswordDto.email());

        checkConfirmationCode(user, changePasswordDto.code(), "forget:password:");

        user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
        userRepository.save(user);

        return ApiResponseDto.success(userMapper.toProfileDto(user));
    }

    private @NonNull User getUser(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.isEnabled())
            throw new MyBadRequestException("User not found");

        return user;
    }
}
