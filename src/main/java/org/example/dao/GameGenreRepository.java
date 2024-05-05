package org.example.dao;

import org.example.model.GameGenre;

import java.util.List;

public interface GameGenreRepository {
    void addGenreToGame(Long gameId, Long genreId);

    void removeGenreFromGame(Long gameId, Long genreId);

    List<GameGenre> getGenresByGameId(Long gameId);
}
