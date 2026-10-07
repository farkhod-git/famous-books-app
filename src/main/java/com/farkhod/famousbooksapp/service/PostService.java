package com.farkhod.famousbooksapp.service;

import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.posts.CreatePostDto;
import com.farkhod.famousbooksapp.payload.posts.PostDto;
import com.farkhod.famousbooksapp.payload.posts.PostSearchDto;
import com.farkhod.famousbooksapp.payload.posts.UpdatePostDto;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PostService {
    ApiResponseDto<PagedModel<PostDto>> getPosts(PostSearchDto postSearchDto);

    ApiResponseDto<PostDto> createPost(CreatePostDto createPostDto);

    ApiResponseDto<PostDto> getById(Long id);

    ApiResponseDto<PostDto> changeActive(Long id, boolean active);

    Post getPostById(Long id);

    ApiResponseDto<List<PostDto>> myPosts();

    void incrementComments(Long postId);

    void decrementComments(Long postId);

    ApiResponseDto<PostDto> updatePost(Long id, UpdatePostDto updatePostDto);

    ApiResponseDto<PostDto> updatePhotos(Long id, Set<UUID> photoIds);
}
