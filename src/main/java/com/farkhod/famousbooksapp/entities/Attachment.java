package com.farkhod.famousbooksapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "attachments")
@EntityListeners(AuditingEntityListener.class)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String originalName;

    @Column(nullable = false)
    String filename;

    @Column(nullable = false)
    String path;

    @Column(nullable = false)
    String contentType;

    long size;

    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime createdAt;

    @CreatedBy
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    User createdBy;
}
