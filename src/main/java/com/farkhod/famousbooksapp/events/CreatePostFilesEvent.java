package com.farkhod.famousbooksapp.events;

import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.payload.posts.CreatePostDto;

public record CreatePostFilesEvent(Post post, CreatePostDto createPostDto) {
}
