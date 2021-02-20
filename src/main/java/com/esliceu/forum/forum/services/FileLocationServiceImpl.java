package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.entities.UserImg;
import com.esliceu.forum.forum.repos.FileSystemRepo;
import com.esliceu.forum.forum.repos.ImageResourceRepo;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;

@Service
public class FileLocationServiceImpl implements FileLocationService{
    @Autowired
    FileSystemRepo fileSystemRepo;
    @Autowired
    ImageResourceRepo imageResourceRepo;

    @Override
    public Long save(byte[] bytes, String imageName, User user) throws Exception {
        String location = fileSystemRepo.save(bytes, imageName);

        UserImg image = new UserImg();
        image.setLocation(location);
        image.setName(imageName);
        image.setUser(user);

        return imageResourceRepo.save(image).getId();
    }

    @Override
    public FileSystemResource find(Long imageId) {
        UserImg image = imageResourceRepo.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepo.findInFileSystem(image.getLocation());
    }
}
