package com.farkhod.famousbooksapp.entities;

import com.farkhod.famousbooksapp.entities.embedded.PostUserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "post_likes")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class PostLike {
    public PostLike(PostUserId id) {
        this.id = id;
    }

    @EmbeddedId
    PostUserId id;

    @CreationTimestamp
    LocalDateTime createdAt;
}
