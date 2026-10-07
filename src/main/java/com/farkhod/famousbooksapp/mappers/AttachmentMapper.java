package com.farkhod.famousbooksapp.mappers;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.payload.attachment.AttachmentDto;
import com.farkhod.famousbooksapp.repositories.projection.CommentProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    AttachmentDto toDto(Attachment attachment);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", source = "fileId")
    AttachmentDto toDto(CommentProjection commentProjection);
}
