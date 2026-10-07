package com.farkhod.famousbooksapp.entities;

import com.farkhod.famousbooksapp.enums.BookGenreEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String bookName;

    String author;

    String opinion;

    Integer pages;

    @Enumerated(EnumType.STRING)
    BookGenreEnum genre;

    Short year;

    int readerScore;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    Attachment cover;

    long views;

    long likes;

    long comments;

    boolean active;

    @Column(name = "search_vector", insertable = false, updatable = false)
    private String searchVector;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_photos",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    List<Attachment> photos;

    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    @CreatedBy
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    User createdBy;
}
