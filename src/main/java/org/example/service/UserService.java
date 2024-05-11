package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.UserRepository;
import org.example.DTO.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    private final String salt;
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService(UserRepository userRepository, String salt) {
        this.userRepository = userRepository;
        this.salt = salt;
    }

    public UserDTO getUserByCredentials(String email, String password) {
        try {
            User user = userRepository.findByEmail(email);
            if (user != null && user.getPassword().equals(hashPassword(password))) {
                return UserMapper.toDTO(user);
            }
            logger.warn("No user found or invalid password for email: {}", email);
            return null;
        } catch (Exception e) {
            logger.error("Error retrieving user by credentials", e);
            throw e;
        }
    }

    public UserDTO getUserById(Long userId) {
        try {
            User user = userRepository.getUserById(userId);
            return UserMapper.toDTO(user);
        } catch (Exception e) {
            logger.error("Error retrieving user by ID: {}", userId, e);
            throw e;
        }
    }

    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.getAllUsers();
            return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving all users", e);
            throw e;
        }
    }

    public void saveUser(UserDTO userDTO) {
        try {
            User user = UserMapper.toEntity(userDTO);
            String hashedPassword = hashPassword(userDTO.getPassword());
            user.setPassword(hashedPassword);
            userRepository.saveUser(user);
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw e;
        }
    }

    public void updateUser(UserDTO userDTO) {
        try {
            User user = UserMapper.toEntity(userDTO);
            String hashedPassword = hashPassword(userDTO.getPassword());
            user.setPassword(hashedPassword);
            userRepository.updateUser(user);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            throw e;
        }
    }

    public void deleteUser(Long userId) {
        try {
            userRepository.deleteUser(userId);
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", userId, e);
            throw e;
        }
    }

    public boolean registerNewUser(UserDTO userDTO) {
        try {
            if (userRepository.findByEmail(userDTO.getEmail()) != null) {
                return false;
            }
            String hashedPassword = hashPassword(userDTO.getPassword());
            userDTO.setPassword(hashedPassword);
            User user = UserMapper.toEntity(userDTO);
            userRepository.saveUser(user);
            return true;
        } catch (Exception e) {
            logger.error("Error registering new user", e);
            throw e;
        }
    }

    private String hashPassword(String password) {
        return String.valueOf((salt + password).hashCode());
    }
}
