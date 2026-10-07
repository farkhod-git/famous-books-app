package com.farkhod.famousbooksapp.service;

import java.util.UUID;

public interface PostViewService {
    boolean seePost(Long id, UUID userId);
}
