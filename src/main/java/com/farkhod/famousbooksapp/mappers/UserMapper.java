package com.farkhod.famousbooksapp.mappers;

import com.farkhod.famousbooksapp.entities.User;
import com.farkhod.famousbooksapp.payload.ProfileUpdateDto;
import com.farkhod.famousbooksapp.payload.auth.ProfileDto;
import com.farkhod.famousbooksapp.payload.auth.RegisterDto;
import com.farkhod.famousbooksapp.repositories.projection.CommentProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarId", source = "avatar.id")
    ProfileDto toProfileDto(User user);

    User toEntity(RegisterDto registerDto);

    void update(RegisterDto registerDto, @MappingTarget User user);

    @Mapping(target = "id", source = "createdById")
    @Mapping(target = "firstname", source = "createdByFirstname")
    @Mapping(target = "lastname", source = "createdByLastname")
    @Mapping(target = "avatarId", source = "createdByAvatarId")
    ProfileDto toProfileDto(CommentProjection commentProjection);

    void updateUser(ProfileUpdateDto profileUpdateDto, @MappingTarget User user);
}
