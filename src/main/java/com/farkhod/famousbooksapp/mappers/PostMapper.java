package com.farkhod.famousbooksapp.mappers;

import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.payload.posts.CreatePostDto;
import com.farkhod.famousbooksapp.payload.posts.PostDto;
import com.farkhod.famousbooksapp.payload.posts.UpdatePostDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AttachmentMapper.class})
public interface PostMapper {
    Post toPost(CreatePostDto createPostDto);

    PostDto toPostDto(Post post);

    void update(UpdatePostDto updatePostDto, @MappingTarget Post post);
}
