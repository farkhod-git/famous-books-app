package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.repositories.PostViewRepository;
import com.farkhod.famousbooksapp.service.PostViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostViewServiceImpl implements PostViewService {
    private final PostViewRepository postViewRepository;

    @Override
    public boolean seePost(Long id, UUID userId) {
        return postViewRepository.seePost(id, userId);
    }
}
