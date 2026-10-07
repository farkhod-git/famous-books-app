package com.farkhod.famousbooksapp.repositories;

import com.farkhod.famousbooksapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
