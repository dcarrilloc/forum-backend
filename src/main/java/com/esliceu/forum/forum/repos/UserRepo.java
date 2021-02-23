package com.esliceu.forum.forum.repos;

import com.esliceu.forum.forum.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByAvatar(String image);
}
