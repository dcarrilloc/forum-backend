package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email){
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
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
    public boolean checkRegisterCredentials(String email, String password, String moderateCategory, String name, String role) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        return optionalUser.isEmpty();
    }

    @Override
    public void register(String email, String password, String moderateCategory, String name, String role) {
        User user = new User();
        user.setAvatar("");
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);
        userRepo.save(user);
    }

    @Override
    public void updateProfile(String email, String name) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(email);
            user.setName(name);
            userRepo.save(user);
        }
    }

    @Override
    public void updatePassword(String email, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(currentPassword)) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepo.save(user);
        }
    }
}
