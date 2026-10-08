package com.farkhod.famousbooksapp.repositories.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CommentProjection {
    Long getId();

    UUID getFileId();

    Long getReplyCommentId();

    String getContent();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    UUID getCreatedById();

    String getCreatedByFirstname();

    String getCreatedByLastname();

    UUID getCreatedByAvatarId();

    boolean isDeleted();
}
