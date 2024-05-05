package org.example.service;

import org.example.dao.UserRepository;
import org.example.DTO.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    private final String  salt;
    public UserService(UserRepository userRepository, String salt) {
        this.userRepository = userRepository;
        this.salt = salt;
    }

    public UserDTO getUserByCredentials(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            return UserMapper.toDTO(user);
        }
        return null;
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.getUserById(userId);
        return UserMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public void saveUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        userRepository.saveUser(user);
    }

    public void updateUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        userRepository.updateUser(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    public boolean registerNewUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            return false;
        }

        String hashedPassword = hashPassword(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);

        User user = UserMapper.toEntity(userDTO);
        userRepository.saveUser(user);
        return true;
    }

    private String hashPassword(String password) {
        return String.valueOf((salt+password).hashCode());
    }
}
