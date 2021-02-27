package com.esliceu.forum.forum.services;

import com.esliceu.forum.forum.entities.Category;
import com.esliceu.forum.forum.entities.User;
import com.esliceu.forum.forum.repos.CategoryRepo;
import com.esliceu.forum.forum.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
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
    public boolean checkCredentials(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        return optionalUser.isPresent() && validatePassword(password, optionalUser.get().getPassword());
    }

    @Override
    public boolean checkRegisterCredentials(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        return optionalUser.isEmpty();
    }

    @Override
    public User register(String email, String password, String name, String role) throws NoSuchAlgorithmException {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(encryptPassword(password));
        user.setRole(role);
        user.setAvatar(new byte[0]);
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
        if (optionalUser.isPresent() && validatePassword(currentPassword, optionalUser.get().getPassword())) {
            User user = optionalUser.get();
            user.setPassword(encryptPassword(newPassword));
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
        Charset charset = StandardCharsets.US_ASCII;
        try {
            Optional<User> optionalUser = userRepo.findById(userid);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                byte[] img = charset.encode(avatar).array();
                user.setAvatar(img);
                userRepo.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getUserImage(Long userid) {
        Optional<User> optionalUser = userRepo.findById(userid);
        return optionalUser.map(User::getAvatar).orElse(null);
    }

    private String encryptPassword(String pass) throws NoSuchAlgorithmException {
        byte[] salt = getSalt();
        int iterations = 100000;
        int keyLength = 64 * 8;
        char[] passwordChars = pass.toCharArray();

        return hashPassword(passwordChars, salt, iterations, keyLength);
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = 100000;
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static String hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength ) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return toHex(salt) + ":" + toHex(hash);
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
