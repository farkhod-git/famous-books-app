package com.farkhod.famousbooksapp.controller;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.auth.*;
import com.farkhod.famousbooksapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ApiResponseDto<ProfileDto> register(@Valid @RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/confirm")
    public ApiResponseDto<TokenDto> confirm(@Valid @RequestBody ConfirmDto confirmDto) {
        return userService.confirm(confirmDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ApiResponseDto<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/password/forget")
    public ApiResponseDto<Object> forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto) {
        return userService.forgetPassword(forgetPasswordDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/password/change")
    public ApiResponseDto<ProfileDto> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(changePasswordDto);
    }
}
