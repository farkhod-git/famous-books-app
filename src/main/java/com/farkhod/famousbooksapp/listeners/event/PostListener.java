package com.farkhod.famousbooksapp.listeners.event;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.events.CreatePostFilesEvent;
import com.farkhod.famousbooksapp.events.UpdatePostFilesEvent;
import com.farkhod.famousbooksapp.exceptions.MyBadRequestException;
import com.farkhod.famousbooksapp.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostListener {

    private final AttachmentService attachmentService;

    @EventListener
    public void onCreatePostEventSetCoverListener(CreatePostFilesEvent postFilesEvent) {
        Post post = postFilesEvent.post();

        // set cover
        UUID coverId = postFilesEvent.createPostDto().coverId();
        Attachment cover = attachmentService.getById(coverId);
        post.setCover(cover);
    }

    @EventListener
    public void onCreatePostEventSetPhotosListener(CreatePostFilesEvent postFilesEvent) {
        Post post = postFilesEvent.post();

        // set photos
        Set<UUID> photoIds = postFilesEvent.createPostDto().photoIds();
        if (photoIds != null && !photoIds.isEmpty()) {
            List<Attachment> photos = attachmentService.getAllByIds(photoIds);
            if (photos.size() != photoIds.size())
                throw new MyBadRequestException("Some photos were not found");

            post.setPhotos(photos);
        }
    }

    @EventListener
    public void onCreatePostEventSetBookFileListener(CreatePostFilesEvent postFilesEvent) {
        Post post = postFilesEvent.post();

        // set book file
        UUID bookFileId = postFilesEvent.createPostDto().bookFileId();
        Attachment bookFile = attachmentService.getById(bookFileId);
        post.setBookFile(bookFile);
    }

    @EventListener
    public void onUpdatePostEventSetCoverListener(UpdatePostFilesEvent updatePostFilesEvent) {
        Post post = updatePostFilesEvent.post();

        UUID coverId = updatePostFilesEvent.updatePostDto().coverId();
        if (!Objects.equals(post.getCover().getId(), coverId)) {
            Attachment cover = attachmentService.getById(coverId);
            post.setCover(cover);
        }
    }

    @EventListener
    public void onUpdatePostEventSetBookFileListener(UpdatePostFilesEvent updatePostFilesEvent) {
        Post post = updatePostFilesEvent.post();

        UUID bookFileId = updatePostFilesEvent.updatePostDto().bookFileId();
        if (bookFileId != null && !Objects.equals(post.getBookFile().getId(), bookFileId)) {
            Attachment bookFile = attachmentService.getById(bookFileId);
            post.setBookFile(bookFile);
        }
    }

}
