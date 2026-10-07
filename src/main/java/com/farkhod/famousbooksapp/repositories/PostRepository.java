package com.farkhod.famousbooksapp.repositories;

import com.farkhod.famousbooksapp.entities.Post;
import com.farkhod.famousbooksapp.payload.posts.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndCreatedBy_Id(Long id, UUID createdById);

    String POST_SEARCH_BASE_QUERY = """
            select new com.farkhod.famousbooksapp.payload.posts.PostDto(
                        p.id,
                        p.bookName,
                        p.author,
                        p.opinion,
                        p.pages,
                        p.genre,
                        p.year,
                        p.readerScore,
                        null,
                        p.cover.id,
                        p.views,
                        p.likes,
                        p.comments,
                        p.active,
                        null ,
                        p.createdAt,
                        p.updatedAt,
                        (l.id.postId is not null),
                        (v.id.postId is not null)
                      )
            """;

    @Query(value = POST_SEARCH_BASE_QUERY + """
            from Post p
                left join PostLike l on l.id.postId = p.id and l.id.userId = :userId
                left join PostView v on v.id.postId = p.id and v.id.userId = :userId
            where p.active = true
            and FUNCTION('ts_match_vq', p.searchVector, cast(:query as string)) = true""",
            countQuery = """
                    select count(p.id)
                    from Post p
                    where p.active = true
                    and FUNCTION('ts_match_vq', p.searchVector, cast(:query as string)) = true""")
    Page<PostDto> findAllPostsByQuery(UUID userId, String query, Pageable pageable);

    @Query(value = POST_SEARCH_BASE_QUERY + """
            from Post p
                left join PostLike l on l.id.postId = p.id and l.id.userId = :userId
                left join PostView v on v.id.postId = p.id and v.id.userId = :userId
            where p.active = true""",
            countQuery = """
                    select count(p.id)
                    from Post p
                    where p.active = true""")
    Page<PostDto> findAllPosts(UUID userId, Pageable pageable);

    @Query(value = """
            select new com.farkhod.famousbooksapp.payload.posts.PostDto(
                        p.id,
                        p.bookName,
                        p.author,
                        p.opinion,
                        p.pages,
                        p.genre,
                        p.year,
                        p.readerScore,
                        null,
                        p.cover.id,
                        p.views,
                        p.likes,
                        p.comments,
                        p.active,
                        null ,
                        p.createdAt,
                        p.updatedAt,
                        (l.id.postId is not null),
                        (v.id.postId is not null)
                       )
            from Post p
                left join PostLike l on l.id.postId = p.id and l.id.userId = :userId
                left join PostView v on v.id.postId = p.id and v.id.userId = :userId
            where p.createdBy.id = :userId
            order by p.createdAt desc
            """)
    List<PostDto> findMyPosts(UUID userId);

    @Modifying
    @Query(nativeQuery = true, value = """
            update posts
            set comments = comments + 1
            where id = :id""")
    void incrementComments(Long id);

    @Modifying
    @Query(nativeQuery = true, value = """
            update posts
            set comments = comments - 1
            where id = :id""")
    void decrementComments(Long id);

    boolean existsByIdAndActiveIsTrue(Long id);
}
