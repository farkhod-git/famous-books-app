package com.farkhod.famousbooksapp.repositories;

import com.farkhod.famousbooksapp.entities.PostLike;
import com.farkhod.famousbooksapp.entities.embedded.PostUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PostViewRepository extends JpaRepository<PostLike, PostUserId> {

    @Query(nativeQuery = true, value = "select seePost(:id, :userId)")
    boolean seePost(Long id, UUID userId);

}
