package com.farkhod.famousbooksapp.service;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.ProfileUpdateDto;
import com.farkhod.famousbooksapp.payload.auth.*;

public interface UserService {
    ApiResponseDto<ProfileDto> register(RegisterDto registerDto);

    ApiResponseDto<TokenDto> confirm(ConfirmDto confirmDto);

    ApiResponseDto<TokenDto> login(LoginDto loginDto);

    ApiResponseDto<ProfileDto> getMe();

    ApiResponseDto<ProfileDto> updateMe(ProfileUpdateDto profileUpdateDto);

    ApiResponseDto<Object> forgetPassword(ForgetPasswordDto forgetPasswordDto);

    ApiResponseDto<ProfileDto> changePassword(ChangePasswordDto changePasswordDto);
}
