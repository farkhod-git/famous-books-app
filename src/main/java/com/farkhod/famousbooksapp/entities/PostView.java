package com.farkhod.famousbooksapp.entities;

import com.farkhod.famousbooksapp.entities.embedded.PostUserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "post_views")
public class PostView {
    @EmbeddedId
    PostUserId id;

    LocalDateTime createdAt;
}
