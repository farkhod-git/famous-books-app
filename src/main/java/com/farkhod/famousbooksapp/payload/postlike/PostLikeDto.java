package com.farkhod.famousbooksapp.payload.postlike;

import com.farkhod.famousbooksapp.constants.AppConstants;
import com.farkhod.famousbooksapp.entities.embedded.PostUserId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostLikeDto {
    PostUserId id;
    @JsonFormat(pattern = AppConstants.DATE_TIME_FORMAT)
    LocalDateTime createdAt;
}
