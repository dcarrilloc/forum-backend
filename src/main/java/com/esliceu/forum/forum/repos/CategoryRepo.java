package com.esliceu.forum.forum.repos;

import com.esliceu.forum.forum.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
}
