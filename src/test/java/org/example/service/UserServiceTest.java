package org.example.service;

import org.example.dao.UserRepository;
import org.example.DTO.UserDTO;
import org.example.model.User;
import org.example.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final String salt = "some_salt";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, salt);
    }

    @Test
    public void testRegisterNewUser_Success() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(null);

        UserDTO newUser = new UserDTO(null, "TestUser", email, password, LocalDate.now());
        assertTrue(userService.registerNewUser(newUser));
        verify(userRepository).saveUser(any(User.class));
    }

    @Test
    public void testRegisterNewUser_Failure_EmailExists() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(new User());

        UserDTO newUser = new UserDTO(null, "TestUser", email, "password", LocalDate.now());
        assertFalse(userService.registerNewUser(newUser));
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    public void testGetUserByCredentials_ValidCredentials() {
        String email = "test@example.com";
        String password = "password";
        User user = new User(1L, "TestUser", email, String.valueOf((salt+password).hashCode()), LocalDate.now());

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDTO result = userService.getUserByCredentials(email, password);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testGetUserByCredentials_InvalidCredentials() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(null);

        assertNull(userService.getUserByCredentials(email, password));
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = new UserDTO(1L, "UpdatedUser", "update@example.com", "updatedPassword", LocalDate.now());
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(String.valueOf((salt+"updatedPassword").hashCode()));
        doNothing().when(userRepository).updateUser(user);

        userService.updateUser(userDTO);

        verify(userRepository).updateUser(user);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User(1L, "UserOne", "one@example.com", "passOne", LocalDate.now());
        User user2 = new User(2L, "UserTwo", "two@example.com", "passTwo", LocalDate.now());
        when(userRepository.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("UserOne", users.get(0).getName());
        assertEquals("UserTwo", users.get(1).getName());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteUser(userId);

        userService.deleteUser(userId);

        verify(userRepository).deleteUser(userId);
    }
}
