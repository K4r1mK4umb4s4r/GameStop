package org.example.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class GenreRepositoryImplTest {
    private Connection connection;
    private GenreRepositoryImpl repository;

    @BeforeEach
    void setUp() throws Exception {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gamestop", "postgres", "2277");

        // Подготовка базы данных и начальных данных
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS genre (Genre_id SERIAL PRIMARY KEY, title VARCHAR(255), description VARCHAR(255))");
            statement.execute("INSERT INTO genre (genre_id, title, description) VALUES (1, 'Action', 'Action games involving...')");
            statement.execute("INSERT INTO genre (genre_id, title, description) VALUES (2, 'Adventure', 'Adventure games exploring...')");
        }

        repository = new GenreRepositoryImpl(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE genre RESTART IDENTITY CASCADE");
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void getGenreById() throws Exception {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT title FROM genre WHERE genre_id = 1");
            assertTrue(rs.next());
            assertEquals("Action", rs.getString("title"));
        }
    }

    @Test
    void getAllGenres() throws Exception {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM genre");
            assertTrue(rs.next());
            assertEquals(2, rs.getInt("count"));
        }
    }

    @Test
    void saveGenre() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO genre (title, description) VALUES ('Strategy', 'Strategy games requiring...')");
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM genre WHERE title = 'Strategy'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("count"));
        }
    }

    @Test
    void updateGenre() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE genre SET description = 'Updated description' WHERE genre_id = 1");
            ResultSet rs = statement.executeQuery("SELECT description FROM genre WHERE genre_id = 1");
            assertTrue(rs.next());
            assertEquals("Updated description", rs.getString("description"));
        }
    }

    @Test
    void deleteGenre() throws Exception {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM genre WHERE genre_id = 1");
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM genre WHERE genre_id = 1");
            assertTrue(rs.next());
            assertEquals(0, rs.getInt("count"));
        }
    }
}
