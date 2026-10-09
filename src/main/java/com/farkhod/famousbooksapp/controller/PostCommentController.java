package com.farkhod.famousbooksapp.controller;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.comments.CommentDto;
import com.farkhod.famousbooksapp.payload.comments.CreateCommentDto;
import com.farkhod.famousbooksapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponseDto<List<CommentDto>> comments(@PathVariable Long postId,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false, defaultValue = "1000000000") Long lastCommentId) {
        return commentService.comments(postId, size, lastCommentId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto<CommentDto> createComment(@PathVariable Long postId,
                                                    @Valid @RequestBody CreateCommentDto createCommentDto) {
        return commentService.createComment(postId, createCommentDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
    }

}
