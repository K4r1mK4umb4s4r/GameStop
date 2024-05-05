package org.example.dao.impl;

import org.example.exception.DeletionException;
import org.example.exception.SearchException;
import org.example.model.GameGenre;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameGenreRepositoryImplTest {
    private static Connection connection;
    private static GameGenreRepositoryImpl gameGenreRepository;

    @BeforeAll
    static void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/stopgame";
        connection = DriverManager.getConnection(url, "postgres", "2277");
        gameGenreRepository = new GameGenreRepositoryImpl(connection);

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Game (Game_ID SERIAL PRIMARY KEY, Title TEXT NOT NULL, Description TEXT NOT NULL, Price INTEGER NOT NULL, Developer_ID INTEGER NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS Genre (Genre_ID SERIAL PRIMARY KEY, Title TEXT NOT NULL, Description TEXT NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS Game_Genre (Game_ID INTEGER NOT NULL, Genre_ID INTEGER NOT NULL, PRIMARY KEY (Game_ID, Genre_ID), FOREIGN KEY (Game_ID) REFERENCES Game(Game_ID), FOREIGN KEY (Genre_ID) REFERENCES Genre(Genre_ID))");
        }
    }

    @BeforeEach
    void prepareData() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE Game, Genre, Game_Genre RESTART IDENTITY CASCADE");
            statement.execute("INSERT INTO Game (Title, Description, Price, Developer_ID) VALUES ('Test Game', 'Test Description', 100, 1)");
            statement.execute("INSERT INTO Genre (Title, Description) VALUES ('Test Genre', 'Test Genre Description')");
        }
    }

    @Test
    void addGenreToGame() throws Exception {
        gameGenreRepository.addGenreToGame(1L, 1L);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Game_Genre WHERE Game_ID = ? AND Genre_ID = ?")) {
            ps.setInt(1, 1);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals(1, rs.getInt("Game_ID"));
            assertEquals(1, rs.getInt("Genre_ID"));
        }
    }

    @Test
    void removeGenreFromGame() throws Exception {
        gameGenreRepository.addGenreToGame(1L, 1L);
        gameGenreRepository.removeGenreFromGame(1L, 1L);
        try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS count FROM Game_Genre WHERE Game_ID = ? AND Genre_ID = ?")) {
            ps.setInt(1, 1);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals(0, rs.getInt("count"));
        }
    }

    @Test
    void getGenresByGameId() throws Exception {
        gameGenreRepository.addGenreToGame(1L, 1L);
        List<GameGenre> gameGenres = gameGenreRepository.getGenresByGameId(1L);
        assertFalse(gameGenres.isEmpty());
        assertEquals(1, gameGenres.get(0).getGameId());
        assertEquals(1, gameGenres.get(0).getGenreId());
    }

    @Test
    void testAddGenreToGameWithNonexistentGameOrGenre() {
        Exception exception = assertThrows(SearchException.class, () -> gameGenreRepository.addGenreToGame(999L, 999L));
        assertTrue(exception.getMessage().contains("GameGenre not found"));
    }

    @Test
    void testRemoveNonexistentGameGenre() {
        Exception exception = assertThrows(DeletionException.class, () -> gameGenreRepository.removeGenreFromGame(999L, 999L));
        assertTrue(exception.getMessage().contains("Genre not found"));
    }

    @Test
    void testGetGenresByNonexistentGameId() {
        List<GameGenre> gameGenres = gameGenreRepository.getGenresByGameId(999L);
        assertTrue(gameGenres.isEmpty());
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
