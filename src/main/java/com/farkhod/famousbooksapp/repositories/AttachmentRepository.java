package com.farkhod.famousbooksapp.repositories;

import com.farkhod.famousbooksapp.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
}
