package com.farkhod.famousbooksapp.payload.comments;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateCommentDto(UUID fileId,
                               Long replyCommentId,
                               @NotBlank(message = "Content cannot be blank")
                               String content) {
}
