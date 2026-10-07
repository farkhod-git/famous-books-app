package com.farkhod.famousbooksapp.service;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.comments.CommentDto;
import com.farkhod.famousbooksapp.payload.comments.CreateCommentDto;

import java.util.List;

public interface CommentService {
    ApiResponseDto<List<CommentDto>> comments(Long postId, int size, Long lastCommentId);

    ApiResponseDto<CommentDto> createComment(Long postId, CreateCommentDto createCommentDto);

    void deleteComment(Long postId, Long commentId);
}
