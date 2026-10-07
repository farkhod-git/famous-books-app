package com.farkhod.famousbooksapp.controller;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{id}/likes")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{liked}")
    public ApiResponseDto<Object> likeUnlike(@PathVariable Long id, @PathVariable boolean liked) {
        return postLikeService.likeUnlike(id, liked);
    }

}
