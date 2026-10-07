package com.farkhod.famousbooksapp.repositories;

import com.farkhod.famousbooksapp.entities.PostLike;
import com.farkhod.famousbooksapp.entities.embedded.PostUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PostLikeRepository extends JpaRepository<PostLike, PostUserId> {

    @Query(value = "select likeUnlikePost(:id, :userId, :liked)", nativeQuery = true)
    boolean likeUnlikePost(Long id, UUID userId, boolean liked);
}
