package org.example.dao.impl;

import org.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    private Connection connection;
    private UserRepositoryImpl repository;

    @BeforeEach
    void setUp() throws Exception {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gamestop", "postgres", "2277");

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (user_id SERIAL PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255), date_of_registration DATE)");
            statement.execute("INSERT INTO \"User\" (name, email, password, date_of_registration) VALUES ('Vasya Sasya', 'VasyaSasya@gmail.com', 'hashedpassword', DATE '2021-01-01')");
        }

        repository = new UserRepositoryImpl(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE \"User\" RESTART IDENTITY CASCADE");
        }
    }

    @Test
    void findByEmail() throws Exception {
        User user = repository.findByEmail("VasyaSasya@gmail.com");
        assertNotNull(user);
        assertEquals("Vasya Sasya", user.getName());
    }

    @Test
    void getUserById() throws Exception {
        User user = repository.getUserById(1L);
        assertNotNull(user);
        assertEquals("VasyaSasya@gmail.com", user.getEmail());
    }

    @Test
    void getAllUsers() throws Exception {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM \"User\"");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        }
    }

    @Test
    void saveUser() throws Exception {
        repository.saveUser(new User(null, "Vusya Sasya", "VusyaSasya@gmail.com", "anotherpassword", LocalDate.now()));
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM \"User\" WHERE email = 'VusyaSasya@gmail.com'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        }
    }

    @Test
    void updateUser() throws Exception {
        User user = new User(1L, "Vasya Updated", "VasyaSasya@gmail.com", "updatedpassword", LocalDate.parse("2021-01-01"));
        repository.updateUser(user);
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT name FROM \"User\" WHERE user_id = 1");
            assertTrue(rs.next());
            assertEquals("Vasya Updated", rs.getString("name"));
        }
    }

    @Test
    void deleteUser() throws Exception {
        repository.deleteUser(1L);
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM \"User\" WHERE user_id = 1");
            assertTrue(rs.next());
            assertEquals(0, rs.getInt("count"));
        }
    }
}
