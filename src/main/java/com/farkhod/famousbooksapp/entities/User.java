package com.farkhod.famousbooksapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String firstname;

    String lastname;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    LocalDate birthdate;

    String address;

    boolean enabled;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Attachment avatar;

    @NonNull
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public String getUsername() {
        return email;
    }
}
