package org.example.dao.impl;

import org.example.dao.GenreRepository;
import org.example.exception.DeletionException;
import org.example.exception.InsertionException;
import org.example.exception.SearchException;
import org.example.exception.UpdateException;
import org.example.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {

    private final Connection connection;

    public GenreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Genre getGenreById(Long genreId) {
        final String query = "SELECT * FROM Genre WHERE Genre_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, genreId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Genre(
                            resultSet.getLong("Genre_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SearchException("Genre Not Found");
        }
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        final String query = "SELECT * FROM Genre";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Genre genre = new Genre(
                        resultSet.getLong("Genre_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description")
                );
                genres.add(genre);
            }
        } catch (SQLException e) {
            throw new SearchException("Genre Not Found");
        }
        return genres;
    }

    @Override
    public void saveGenre(Genre genre) {
        final String query = "INSERT INTO Genre (Title, Description) VALUES ( ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, genre.getTitle());
            statement.setString(2, genre.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InsertionException("Can't save Genre");
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        final String query = "UPDATE Genre SET Title = ?, Description = ? WHERE Genre_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, genre.getTitle());
            statement.setString(2, genre.getDescription());
            statement.setLong(3, genre.getGenreId());
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new UpdateException("Genre not found");
            }
        } catch (SQLException e) {
            throw new UpdateException("Can't update Genre");
        }
    }

    @Override
    public void deleteGenre(Long genreId) {
        final String query = "DELETE FROM Genre WHERE Genre_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, genreId);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new DeletionException("Genre not found");
            }
        } catch (SQLException e) {
            throw new DeletionException("Can't delete Genre");
        }
    }
}
