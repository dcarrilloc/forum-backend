package com.esliceu.forum.forum.repos;

import com.esliceu.forum.forum.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    //List<Category> f
}
