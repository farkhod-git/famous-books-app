package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.exceptions.MyBadRequestException;
import com.farkhod.famousbooksapp.exceptions.MyNotFoundException;
import com.farkhod.famousbooksapp.mappers.AttachmentMapper;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.attachment.AttachmentDto;
import com.farkhod.famousbooksapp.payload.attachment.ByteArrayAttachmentDto;
import com.farkhod.famousbooksapp.repositories.AttachmentRepository;
import com.farkhod.famousbooksapp.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    @Value("${file.base-path}")
    private String fileBasePath;

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public Attachment save(ByteArrayAttachmentDto byteArrayAttachmentDto) {
        Path path = saveFile(new ByteArrayInputStream(byteArrayAttachmentDto.getContent()), byteArrayAttachmentDto.getExtension());

        Attachment attachment = new Attachment();
        attachment.setContentType(byteArrayAttachmentDto.getContentType());
        attachment.setOriginalName(byteArrayAttachmentDto.getFilename());
        attachment.setFilename(path.getFileName().toString());
        attachment.setPath(path.toString());
        attachment.setSize(byteArrayAttachmentDto.getContent().length);
        attachment.setCreatedBy(byteArrayAttachmentDto.getUser());
        attachmentRepository.save(attachment);

        return attachment;
    }

    @Override
    public ResponseEntity<Resource> download(UUID id, boolean attachmentDownload) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Attachment not found"));

        ContentDisposition cd = (attachmentDownload ? ContentDisposition.attachment()
                : ContentDisposition.inline())
                .filename(attachment.getOriginalName())
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, cd.toString())
                .body(new FileSystemResource(fileBasePath + attachment.getPath()));
    }

    @Override
    public ApiResponseDto<AttachmentDto> upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.matches("[a-zA-Z0-9-._]+")) {
                throw new MyBadRequestException("Invalid file name");
            }

            Path path = saveFile(file.getInputStream(), extension(originalFilename));

            Attachment attachment = new Attachment();
            attachment.setOriginalName(originalFilename);
            attachment.setFilename(path.getFileName().toString());
            attachment.setPath(path.toString());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            attachmentRepository.save(attachment);

            return ApiResponseDto.success(attachmentMapper.toDto(attachment));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    private Path saveFile(InputStream is, String extension) {
        LocalDate today = LocalDate.now();

        Path path = Path.of("uploads")
                .resolve(today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                .resolve(UUID.randomUUID() + "." + extension);

        Path full = Path.of(fileBasePath).resolve(path);

        try {
            Files.createDirectories(full.getParent());
            Files.copy(is, full, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Path.of("/").resolve(path);
    }

    public Attachment getById(UUID id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Attachment not found"));
    }

    public List<Attachment> getAllByIds(Set<UUID> ids) {
        return attachmentRepository.findAllById(ids);
    }
}
