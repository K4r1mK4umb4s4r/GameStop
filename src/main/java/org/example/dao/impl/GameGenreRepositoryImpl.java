package org.example.dao.impl;

import org.example.dao.GameGenreRepository;
import org.example.exception.DeletionException;
import org.example.exception.SearchException;
import org.example.model.GameGenre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameGenreRepositoryImpl implements GameGenreRepository {

    private final Connection connection;

    public GameGenreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addGenreToGame(Long gameId, Long genreId) {
        final String query = "INSERT INTO Game_Genre (Game_ID, Genre_ID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, gameId);
            statement.setLong(2, genreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SearchException("GameGenre not found");
        }
    }

    @Override
    public void removeGenreFromGame(Long gameId, Long genreId) {
        final String query = "DELETE FROM Game_Genre WHERE Game_ID = ? AND Genre_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, gameId);
            statement.setLong(2, genreId);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new DeletionException("Genre not found");
            }
        } catch (SQLException e) {
            throw new DeletionException("Cant't delete GameGenre");
        }
    }

    @Override
    public List<GameGenre> getGenresByGameId(Long gameId) {
        List<GameGenre> gameGenres = new ArrayList<>();
        final String query = "SELECT * FROM Game_Genre WHERE Game_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GameGenre gameGenre = new GameGenre(
                            resultSet.getLong("Game_ID"),
                            resultSet.getLong("Genre_ID")
                    );
                    gameGenres.add(gameGenre);
                }
            }
        } catch (SQLException e) {
            throw new DeletionException("Cant't get GameGenres");
        }
        return gameGenres;
    }
}
