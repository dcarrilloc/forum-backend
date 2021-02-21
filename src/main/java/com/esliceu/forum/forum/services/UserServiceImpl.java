package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.CategoryRepo;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public boolean checkCredentials(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(password);
    }

    @Override
    public boolean checkRegisterCredentials(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        return optionalUser.isEmpty();
    }

    @Override
    public User register(String email, String password, String name, String role) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);
        user.setAvatar("");
        return userRepo.save(user);
    }

    @Override
    public void updateProfile(Long userid, String email, String name) {
        Optional<User> optionalUser = userRepo.findById(userid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(email);
            user.setName(name);
            userRepo.save(user);
        }
    }

    @Override
    public void updatePassword(Long userid, String currentPassword, String newPassword) throws Exception {
        Optional<User> optionalUser = userRepo.findById(userid);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(currentPassword)) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepo.save(user);
        } else {
            throw new Exception();
        }
    }

    @Override
    public void addModerator(User user, String moderateCategory) {
        Optional<Category> optionalCategory = categoryRepo.findBySlug(moderateCategory);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            Set<User> moderators = category.getModerators();
            moderators.add(user);
            category.setModerators(moderators);
            categoryRepo.save(category);
        }
    }

    @Override
    public void updateProfileImage(String avatar, Long userid) {
        try {
            User user = userRepo.findById(userid).get();
            user.setAvatar(avatar);
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
