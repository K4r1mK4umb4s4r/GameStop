package org.example.dao.impl;

import org.example.exception.DeletionException;
import org.example.exception.InsertionException;
import org.example.exception.UpdateException;
import org.example.model.Developer;
import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeveloperRepositoryImplTest {
    private static Connection connection;
    private static DeveloperRepositoryImpl developerRepository;

    @BeforeAll
    static void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/gamestop";
        connection = DriverManager.getConnection(url, "postgres", "2277");
        developerRepository = new DeveloperRepositoryImpl(connection);

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Developer (Developer_ID SERIAL PRIMARY KEY, Title TEXT NOT NULL, Website TEXT NOT NULL)");
        }
    }

    @BeforeEach
    void prepareData() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE Developer RESTART IDENTITY CASCADE");
        }
    }

    @Test
    void testSaveDeveloper() throws SQLException {
        Developer developer = new Developer(null, "Example Inc.", "http://example.com");
        developerRepository.saveDeveloper(developer);

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM Developer");
            assertTrue(rs.next());
            assertEquals("Example Inc.", rs.getString("Title"));
            assertEquals("http://example.com", rs.getString("Website"));
        }
    }

    @Test
    void testGetDeveloperById() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO Developer (Title, Website) VALUES ('Example Inc.', 'http://example.com')");
        }

        Developer developer = developerRepository.getDeveloperById(1);
        assertNotNull(developer);
        assertEquals("Example Inc.", developer.getTitle());
        assertEquals("http://example.com", developer.getWebsite());
    }

    @Test
    void testUpdateDeveloper() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO Developer (Title, Website) VALUES ('Old Name', 'http://oldurl.com')");
        }

        Developer updatedDeveloper = new Developer(1L, "New Name", "http://newurl.com");
        developerRepository.updateDeveloper(updatedDeveloper);

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM Developer WHERE Developer_ID = 1");
            assertTrue(rs.next());
            assertEquals("New Name", rs.getString("Title"));
            assertEquals("http://newurl.com", rs.getString("Website"));
        }
    }

    @Test
    void testDeleteDeveloper() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO Developer (Title, Website) VALUES ('Example Inc.', 'http://example.com')");
        }

        developerRepository.deleteDeveloper(1L);

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS count FROM Developer");
            assertTrue(rs.next());
            assertEquals(0, rs.getInt("count"));
        }
    }

    @Test
    void testSaveDeveloperWithNullData() {
        Exception exception = assertThrows(InsertionException.class, () -> {
            Developer developer = new Developer(null, null, null);
            developerRepository.saveDeveloper(developer);
        });

    }

    @Test
    void testUpdateNonExistentDeveloper() {
        Exception exception = assertThrows(UpdateException.class, () -> {
            Developer developer = new Developer(999L, "Nonexistent", "http://nonexistent.com");
            developerRepository.updateDeveloper(developer);
        });

    }

    @Test
    void testDeleteNonExistentDeveloper() {
        Exception exception = assertThrows(DeletionException.class, () -> {
            developerRepository.deleteDeveloper(999L);
        });

    }

    @Test
    void testGetDeveloperByNonExistentId() {
        Developer developer = developerRepository.getDeveloperById(999);
        assertNull(developer);
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
