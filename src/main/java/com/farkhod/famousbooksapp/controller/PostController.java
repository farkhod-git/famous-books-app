package com.farkhod.famousbooksapp.controller;

import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.posts.CreatePostDto;
import com.farkhod.famousbooksapp.payload.posts.PostDto;
import com.farkhod.famousbooksapp.payload.posts.PostSearchDto;
import com.farkhod.famousbooksapp.payload.posts.UpdatePostDto;
import com.farkhod.famousbooksapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiResponseDto<PagedModel<PostDto>> getPosts(PostSearchDto postSearchDto) {
        return postService.getPosts(postSearchDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponseDto<PostDto> createPost(CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ApiResponseDto<PostDto> getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{id}/active/{active}")
    public ApiResponseDto<PostDto> changeActive(@PathVariable Long id, @PathVariable boolean active) {
        return postService.changeActive(id, active);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/my")
    public ApiResponseDto<List<PostDto>> myPosts() {
        return postService.myPosts();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    public ApiResponseDto<PostDto> updatePost(@PathVariable Long id, @RequestBody UpdatePostDto updatePostDto) {
        return postService.updatePost(id, updatePostDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}/photos")
    public ApiResponseDto<PostDto> updatePhotos(@PathVariable Long id, @RequestBody Set<UUID> photoIds) {
        return postService.updatePhotos(id, photoIds);
    }

}
