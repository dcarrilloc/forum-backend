package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByEmail(String email);
    boolean checkCredentials(String email, String password);
    boolean checkRegisterCredentials(String email);
    User register(String email, String password, String name, String role);
    void updateProfile(Long userid, String email, String name);
    void updatePassword(Long userid, String currentPassword, String newPassword) throws Exception;
    void addModerator(User user, String moderateCategory);
}
