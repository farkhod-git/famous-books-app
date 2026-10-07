package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.entities.Attachment;
import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.entities.embedded.PostUserId;
import com.farkhod.famousbooksapp.exceptions.MyConflictException;
import com.farkhod.famousbooksapp.exceptions.MyNotFoundException;
import com.farkhod.famousbooksapp.mappers.PostMapper;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.posts.CreatePostDto;
import com.farkhod.famousbooksapp.payload.posts.PostDto;
import com.farkhod.famousbooksapp.payload.posts.PostSearchDto;
import com.farkhod.famousbooksapp.payload.posts.UpdatePostDto;
import com.farkhod.famousbooksapp.repositories.PostLikeRepository;
import com.farkhod.famousbooksapp.repositories.PostRepository;
import com.farkhod.famousbooksapp.service.AttachmentService;
import com.farkhod.famousbooksapp.service.PostService;
import com.farkhod.famousbooksapp.service.PostViewService;
import com.farkhod.famousbooksapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostLikeRepository postLikeRepository;
    private final AttachmentService attachmentService;
    private final PostViewService postViewService;

    @Override
    public ApiResponseDto<PagedModel<PostDto>> getPosts(PostSearchDto postSearchDto) {
        String search = postSearchDto.getSearch();
        String query = search == null ? null : search.replaceAll(" +", " | ");

        PageRequest pageRequest = PageRequest.of(
                postSearchDto.getPage(),
                postSearchDto.getSize(),
                Sort.Direction.DESC, "createdAt"
        );

        UUID userId = CurrentUserUtil.currentUser().getId();

        Page<PostDto> postPage = query == null ?
                postRepository.findAllPosts(userId, pageRequest)
                : postRepository.findAllPostsByQuery(userId, query, pageRequest);

        PagedModel<PostDto> data = new PagedModel<>(postPage);

        System.out.println(data.getContent());
        System.out.println(data);

        return ApiResponseDto.success(data);
    }

    @Override
    public ApiResponseDto<PostDto> createPost(CreatePostDto createPostDto) {
        Post post = postMapper.toPost(createPostDto);
        post.setCover(attachmentService.getById(createPostDto.coverId()));

        // photos
        if (createPostDto.photoIds() != null && !createPostDto.photoIds().isEmpty()) {
            List<Attachment> photos = attachmentService.getAllByIds(createPostDto.photoIds());

            if (photos.size() != createPostDto.photoIds().size())
                throw new MyNotFoundException("Some photos not found");

            post.setPhotos(photos);
        }

        postRepository.save(post);

        return ApiResponseDto.success(postMapper.toPostDto(post));
    }

    @Transactional
    @Override
    public ApiResponseDto<PostDto> getById(Long id) {
        Post post = getPostById(id);

        final UUID userId = CurrentUserUtil.currentUser().getId();

        boolean changed = postViewService.seePost(id, userId);

        PostDto postDto = postMapper.toPostDto(post);
        postDto.setLiked(postLikeRepository.existsById(new PostUserId(post.getId(), userId)));
        postDto.setSeen(true);
        postDto.setViews(post.getViews() + (changed ? 1 : 0));
        return ApiResponseDto.success(postDto);
    }

    @Override
    public ApiResponseDto<PostDto> changeActive(Long id, boolean active) {
        Post post = postRepository
                .findByIdAndCreatedBy_Id(id, CurrentUserUtil.currentUser().getId())
                .orElseThrow(() -> new MyNotFoundException("Post not found"));

        if (post.isActive() == active)
            throw new MyConflictException(active ? "Post already active" : "Post already inactive");

        post.setActive(active);
        postRepository.save(post);

        return ApiResponseDto.success(postMapper.toPostDto(post));
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Post not found"));
    }

    @Override
    public ApiResponseDto<List<PostDto>> myPosts() {
        List<PostDto> myPosts = postRepository.findMyPosts(CurrentUserUtil.currentUser().getId());
        return ApiResponseDto.success(myPosts);
    }

    @Override
    public void incrementComments(Long postId) {
        postRepository.incrementComments(postId);
    }

    @Override
    public void decrementComments(Long postId) {
        postRepository.decrementComments(postId);
    }

    @Override
    public ApiResponseDto<PostDto> updatePost(Long id, UpdatePostDto updatePostDto) {
        Post post = postRepository.findByIdAndCreatedBy_Id(id, CurrentUserUtil.currentUser().getId())
                .orElseThrow(() -> new MyNotFoundException("Post not found"));

        postMapper.update(updatePostDto, post);

        if (updatePostDto.coverId() != null && Objects.equals(post.getCover().getId(), updatePostDto.coverId())) {
            Attachment attachment = attachmentService.getById(updatePostDto.coverId());
            if (attachment == null) {
                throw new MyNotFoundException("Attachment not found");
            }
            post.setCover(attachment);
        }

        postRepository.save(post);

        return ApiResponseDto.success(postMapper.toPostDto(post));
    }

    @Override
    public ApiResponseDto<PostDto> updatePhotos(Long id, Set<UUID> photoIds) {
        Post post = postRepository.findByIdAndCreatedBy_Id(id, CurrentUserUtil.currentUser().getId())
                .orElseThrow(() -> new MyNotFoundException("Post not found"));

        List<Attachment> photos = attachmentService.getAllByIds(photoIds);

        if (photos.size() != photoIds.size())
            throw new MyNotFoundException("Some photos not found");

        post.setPhotos(photos);
        postRepository.save(post);
        return ApiResponseDto.success(postMapper.toPostDto(post));
    }
}
