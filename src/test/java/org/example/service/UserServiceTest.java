package org.example.service;

import org.example.dao.UserRepository;
import org.example.DTO.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByCredentials() {
        User user = new User(1L, "Vasya Sasya", "VasyaSasya@gmail.com", "securepassword", null);
        UserDTO userDTO = new UserDTO(1L, "Vasya Sasya", "VasyaSasya@gmail.com", "securepassword", null);
        when(userRepository.findByEmail("Vasya Sasya@gmail.com")).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserByCredentials("Vasya Sasya@gmail.com", "securepassword");
        assertNotNull(result);
        assertEquals("Vasya Sasya", result.getName());

        verify(userRepository).findByEmail("Vasya Sasya@gmail.com");
    }

    @Test
    void getUserById() {
        User user = new User(1L, "Vasya Sasya", "VasyaSasya@gmail.com", "securepassword", null);
        UserDTO userDTO = new UserDTO(1L, "Vasya Sasya", "VasyaSasya@gmail.com", "securepassword", null);
        when(userRepository.getUserById(1L)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());

        verify(userRepository).getUserById(1L);
    }

    @Test
    void getAllUsers() {
        List<User> users = Arrays.asList(new User(1L, "Vasya Sasya", "Sasya@gmail.com", "securepassword", null));
        List<UserDTO> userDTOs = Arrays.asList(new UserDTO(1L, "Vasya Sasya", "Sasya@gmail.com", "securepassword", null));
        when(userRepository.getAllUsers()).thenReturn(users);

        List<UserDTO> results = userService.getAllUsers();
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals("John Doe", results.get(0).getName());

        verify(userRepository).getAllUsers();
    }

    @Test
    void saveUser() {
        UserDTO userDTO = new UserDTO(null, "Vusya Sasya", "VusyaSasya@gmail.com", "newpassword", null);
        User user = new User(null, "Vusya Sasya", "VusyaSasya@gmail.com", "newpassword", null);
        doNothing().when(userRepository).saveUser(any(User.class));
        when(userMapper.toEntity(userDTO)).thenReturn(user);

        userService.saveUser(userDTO);

        verify(userRepository).saveUser(user);
        verify(userMapper).toEntity(userDTO);
    }

    @Test
    void updateUser() {
        UserDTO userDTO = new UserDTO(1L, "Updated Vusya Sasya", "VusyaSasya@gmail.com", "updatedpassword", null);
        User user = new User(1L, "Updated Vusya Sasya", "VusyaSasya@gmail.com", "updatedpassword", null);
        doNothing().when(userRepository).updateUser(any(User.class));
        when(userMapper.toEntity(userDTO)).thenReturn(user);

        userService.updateUser(userDTO);

        verify(userRepository).updateUser(user);
        verify(userMapper).toEntity(userDTO);
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteUser(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteUser(1L);
    }

    @Test
    void registerNewUser() {
        UserDTO newUserDTO = new UserDTO(null, "New User", "new.user@example.com", "userpassword", null);
        User newUser = new User(null, "New User", "new.user@example.com", "userpassword", null);
        when(userRepository.findByEmail("new.user@example.com")).thenReturn(null);
        when(userMapper.toEntity(newUserDTO)).thenReturn(newUser);
        doNothing().when(userRepository).saveUser(newUser);

        boolean result = userService.registerNewUser(newUserDTO);
        assertTrue(result);

        verify(userRepository).findByEmail("new.user@example.com");
        verify(userRepository).saveUser(newUser);
    }
}
