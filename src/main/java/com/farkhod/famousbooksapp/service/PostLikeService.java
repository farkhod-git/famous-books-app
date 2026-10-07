package com.farkhod.famousbooksapp.service;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;

public interface PostLikeService {
    ApiResponseDto<Object> likeUnlike(Long id, boolean liked);
}
