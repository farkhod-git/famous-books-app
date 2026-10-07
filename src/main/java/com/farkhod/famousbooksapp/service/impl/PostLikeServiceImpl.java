package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.exceptions.MyConflictException;
import com.farkhod.famousbooksapp.exceptions.MyNotFoundException;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.repositories.PostLikeRepository;
import com.farkhod.famousbooksapp.repositories.PostRepository;
import com.farkhod.famousbooksapp.service.PostLikeService;
import com.farkhod.famousbooksapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    @Override
    public ApiResponseDto<Object> likeUnlike(Long id, boolean liked) {
        if (!postRepository.existsByIdAndActiveIsTrue(id))
            throw new MyNotFoundException("Post not found");

        boolean changed = postLikeRepository.likeUnlikePost(id, CurrentUserUtil.currentUser().getId(), liked);

        if (!changed) {
            throw new MyConflictException(liked ? "You already liked this post" : "You already unliked this post");
        }

        return ApiResponseDto.success(null);
    }
}
