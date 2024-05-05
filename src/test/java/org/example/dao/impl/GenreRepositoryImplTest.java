package org.example.dao.impl;

import org.example.model.Genre;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class GenreRepositoryImplTest {
    private static Connection connection;
    private static GenreRepositoryImpl genreRepository;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/gamestop";
        connection = DriverManager.getConnection(url, "postgres", "2277");
        genreRepository = new GenreRepositoryImpl(connection);
    }

    @BeforeEach
    void setUp() throws Exception {
        genreRepository = new GenreRepositoryImpl(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE Genre RESTART IDENTITY CASCADE");
            stmt.execute("INSERT INTO Genre (Title, Description) VALUES ('Action', 'Action genre')");
        }
    }

    @AfterAll
    static void globalTearDown() throws Exception {
        connection.close();
    }

    @Test
    void testGetGenreById() throws Exception {
        Genre result = genreRepository.getGenreById(1L);
        assertNotNull(result);
        assertEquals("Action", result.getTitle());
    }

    @Test
    void testGetAllGenres() throws Exception {
        Genre genre = new Genre(null, "Adventure", "Adventure genre");
        genreRepository.saveGenre(genre);

        assertEquals(2, genreRepository.getAllGenres().size());
    }

    @Test
    void testSaveGenre() throws Exception {
        Genre genre = new Genre(null, "Comedy", "Funny stuff");
        genreRepository.saveGenre(genre);

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Genre WHERE Title = ?")) {
            ps.setString(1, "Comedy");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next());
                assertEquals("Funny stuff", rs.getString("Description"));
            }
        }
    }

    @Test
    void testUpdateGenre() throws Exception {
        Genre genre = new Genre(1L, "Action Updated", "Updated genre description");
        genreRepository.updateGenre(genre);

        Genre updatedGenre = genreRepository.getGenreById(1L);
        assertEquals("Action Updated", updatedGenre.getTitle());
        assertEquals("Updated genre description", updatedGenre.getDescription());
    }

    @Test
    void testDeleteGenre() throws Exception {
        genreRepository.deleteGenre(1L);

        assertNull(genreRepository.getGenreById(1L));
    }
}
