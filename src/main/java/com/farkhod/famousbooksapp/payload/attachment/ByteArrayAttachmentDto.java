package com.farkhod.famousbooksapp.payload.attachment;

import com.farkhod.famousbooksapp.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ByteArrayAttachmentDto {
    String filename;
    String contentType;
    byte[] content;
    String extension;
    User user;
}
