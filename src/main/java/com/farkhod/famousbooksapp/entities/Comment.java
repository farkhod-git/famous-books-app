package com.farkhod.famousbooksapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    Attachment file;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Post post;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Comment replyComment;

    @Column(nullable = false)
    String content;

    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    User createdBy;

    boolean deleted;
}
