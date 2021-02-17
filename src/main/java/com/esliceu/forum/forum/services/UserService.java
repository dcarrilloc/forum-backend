package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    boolean checkCredentials(String email, String password);
    boolean checkRegisterCredentials(String email, String password, String moderateCategory, String name, String role);
    void register(String email, String password, String moderateCategory, String name, String role);
}
