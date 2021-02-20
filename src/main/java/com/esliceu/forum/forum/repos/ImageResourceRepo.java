package com.esliceu.forum.forum.repos;

import com.esliceu.forum.forum.entities.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageResourceRepo extends JpaRepository<UserImg, Long> {
}
