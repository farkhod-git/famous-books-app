package com.farkhod.famousbooksapp.repositories;

import com.farkhod.famousbooksapp.entities.Comment;
import com.farkhod.famousbooksapp.repositories.projection.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = """
            WITH RECURSIVE comment_tree AS ((select *
                                             from comments
                                             where post_id = :postId
                                               and reply_comment_id is null
                                               and id <= :commentId
                                               order by id
                                               limit :size)
            
                                            UNION ALL
            
                                            select c.*
                                            from comments c
                                                     inner join comment_tree t
                                                                on t.id = c.reply_comment_id)
            select c.id               as id,
                   c.file_id          as fileId,
                   c.reply_comment_id as replyCommentId,
                   c.content          as content,
                   c.created_at       as createdAt,
                   c.updated_at       as updatedAt,
                   u.id               as createdById,
                   u.firstname        as createdByFirstname,
                   u.lastname         as createdByLastname,
                   u.avatar_id        as createdByAvatarId,
                   c.deleted          as deleted
            from comment_tree c
                     inner join users u on u.id = c.created_by_id""", nativeQuery = true)
    List<CommentProjection> findAllTreeCommentsByPostId(Long postId, int size, Long commentId);

    Optional<Comment> findByIdAndCreatedBy_Id(Long commentId, UUID id);
}
