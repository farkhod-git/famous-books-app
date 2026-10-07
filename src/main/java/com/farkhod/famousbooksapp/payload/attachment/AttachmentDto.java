package com.farkhod.famousbooksapp.payload.attachment;

import com.farkhod.famousbooksapp.constants.AppConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDto {
    UUID id;
    String originalName;
    String filename;
    String path;
    String contentType;
    long size;
    @JsonFormat(pattern = AppConstants.DATE_TIME_FORMAT)
    LocalDateTime createdAt;

    public AttachmentDto(UUID id) {
        this.id = id;
    }
}
