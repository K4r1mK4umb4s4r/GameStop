package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserRepository {
    User findByEmail(String email);

    User getUserById(Long userId);

    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(Long userId);
}
