package com.esliceu.forum.forum.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Expose
    @Column(unique = true)
    private String email;
    @Expose(serialize = false)
    private String password;
    @Expose
    private String name;
    @Expose
    private String avatar;
    @Expose
    private String role;

    // Relació 1-N amb Topic
    @Expose(serialize = false, deserialize = false)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Topic> topics;

    // Relació 1-N amb Topic
    @Expose(serialize = false, deserialize = false)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Reply> replies;

    // Relació N-M amb Category
    @Expose(serialize = false, deserialize = false)
    @ManyToMany(mappedBy = "moderators")
    private Set<Category> categories_moderator;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Reply> getReplies() {
        return replies;
    }

    public void setReplies(Set<Reply> replies) {
        this.replies = replies;
    }

    public Set<Category> getCategories_moderator() {
        return categories_moderator;
    }

    public void setCategories_moderator(Set<Category> categories_moderator) {
        this.categories_moderator = categories_moderator;
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
