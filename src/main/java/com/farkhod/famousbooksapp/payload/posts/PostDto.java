package com.farkhod.famousbooksapp.payload.posts;

import com.farkhod.famousbooksapp.entities.User;
import com.farkhod.famousbooksapp.enums.BookGenreEnum;
import com.farkhod.famousbooksapp.payload.attachment.AttachmentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    Long id;
    String bookName;
    String author;
    String opinion;
    Integer pages;
    BookGenreEnum genre;
    Short year;
    int readerScore;
    AttachmentDto cover;
    UUID coverId;
    long views;
    long likes;
    long comments;
    boolean active;
    List<AttachmentDto> photos;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean liked;
    boolean seen;
}
