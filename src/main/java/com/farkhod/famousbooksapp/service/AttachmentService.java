package com.farkhod.famousbooksapp.service;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.attachment.AttachmentDto;
import com.farkhod.famousbooksapp.payload.attachment.ByteArrayAttachmentDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AttachmentService {
    Attachment save(ByteArrayAttachmentDto byteArrayAttachmentDto);

    ResponseEntity<Resource> download(UUID id, boolean attachmentDownload);

    ApiResponseDto<AttachmentDto> upload(MultipartFile file);

    Attachment getById(UUID id);

    List<Attachment> getAllByIds(Set<UUID> uuids);
}
