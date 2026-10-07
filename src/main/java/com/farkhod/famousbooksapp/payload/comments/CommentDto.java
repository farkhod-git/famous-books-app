package com.farkhod.famousbooksapp.payload.comments;

import com.farkhod.famousbooksapp.constants.AppConstants;
import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.entities.User;
import com.farkhod.famousbooksapp.payload.attachment.AttachmentDto;
import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    AttachmentDto file;
    Long replyCommentId;
    List<CommentDto> replies;
    String content;
    @JsonFormat(pattern = AppConstants.DATE_TIME_FORMAT)
    LocalDateTime createdAt;
    @JsonFormat(pattern = AppConstants.DATE_TIME_FORMAT)
    LocalDateTime updatedAt;
    ProfileDto createdBy;
}
