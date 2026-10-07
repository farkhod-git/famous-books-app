package com.farkhod.famousbooksapp.controller;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.ProfileUpdateDto;
import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.farkhod.famousbooksapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public ApiResponseDto<ProfileDto> getMe() {
        return userService.getMe();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/me")
    public ApiResponseDto<ProfileDto> updateMe(@Valid @RequestBody ProfileUpdateDto profileUpdateDto) {
        return userService.updateMe(profileUpdateDto);
    }

}
