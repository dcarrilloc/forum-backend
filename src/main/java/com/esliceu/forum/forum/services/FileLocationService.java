package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.User;
import org.springframework.core.io.FileSystemResource;

public interface FileLocationService {
    Long save(byte[] bytes, String imageName, User user) throws Exception;
    FileSystemResource find(Long imageId);
}
