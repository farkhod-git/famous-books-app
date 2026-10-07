package com.farkhod.famousbooksapp.mappers;

import com.farkhod.famousbooksapp.entities.Comment;
import com.farkhod.famousbooksapp.payload.comments.CommentDto;
import com.farkhod.famousbooksapp.repositories.projection.CommentProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AttachmentMapper.class, UserMapper.class})
public interface CommentMapper {
    @Mapping(target = "file", source = ".")
    @Mapping(target = "createdBy", source = ".")
    CommentDto toDtoList(CommentProjection commentProjection);

    List<CommentDto> toDtoList(List<CommentProjection> commentProjections);

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    CommentDto toDto(Comment comment);
}
