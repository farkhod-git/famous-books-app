package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.entities.Comment;
import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.exceptions.MyNotFoundException;
import com.farkhod.famousbooksapp.mappers.CommentMapper;
import com.farkhod.famousbooksapp.payload.ApiResponseDto;
import com.farkhod.famousbooksapp.payload.comments.CommentDto;
import com.farkhod.famousbooksapp.payload.comments.CreateCommentDto;
import com.farkhod.famousbooksapp.repositories.CommentRepository;
import com.farkhod.famousbooksapp.service.AttachmentService;
import com.farkhod.famousbooksapp.service.CommentService;
import com.farkhod.famousbooksapp.service.PostService;
import com.farkhod.famousbooksapp.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;
    private final AttachmentService attachmentService;

    @Override
    public ApiResponseDto<List<CommentDto>> comments(Long postId, int size, Long lastCommentId) {
        List<CommentDto> comments = commentMapper.toDtoList(
                commentRepository.findAllTreeCommentsByPostId(postId, size, lastCommentId)
        );

        Map<Long, List<CommentDto>> replies =  new HashMap<>();

        for (CommentDto comment : comments) {
            replies.computeIfAbsent(comment.getReplyCommentId(), _ -> new ArrayList<>())
                    .add(comment);
        }

        for (CommentDto commentDto : comments)
            commentDto.setReplies(replies.get(commentDto.getId()));

        return ApiResponseDto.success(replies.get(null));
    }

    @Transactional
    @Override
    public ApiResponseDto<CommentDto> createComment(Long postId, CreateCommentDto createCommentDto) {
        Post post = postService.getPostById(postId);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(createCommentDto.content());

        if (createCommentDto.fileId() != null) {
            comment.setFile(attachmentService.getById(createCommentDto.fileId()));
        }

        if (createCommentDto.replyCommentId() != null) {
            comment.setReplyComment(getById(createCommentDto.replyCommentId()));
        }

        commentRepository.save(comment);
        postService.incrementComments(postId);

        return ApiResponseDto.success(commentMapper.toDto(comment));
    }

    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId) {
        postService.getPostById(postId);
        Comment comment = commentRepository.findByIdAndCreatedBy_Id(commentId, CurrentUserUtil.currentUser().getId())
                .orElseThrow(() -> new MyNotFoundException("Comment not found"));

        commentRepository.delete(comment);
        postService.decrementComments(postId);
    }

    private Comment getById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Comment not found"));
    }
}
