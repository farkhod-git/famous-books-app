package com.farkhod.famousbooksapp.events;

import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.payload.posts.UpdatePostDto;
import org.springframework.context.ApplicationEvent;

public record UpdatePostFilesEvent(Post post, UpdatePostDto updatePostDto) {
}
