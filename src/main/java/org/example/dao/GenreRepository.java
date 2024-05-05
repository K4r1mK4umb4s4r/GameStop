package org.example.dao;

import org.example.model.Genre;

import java.util.List;

public interface GenreRepository {
    Genre getGenreById(Long genreId);

    List<Genre> getAllGenres();

    void saveGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenre(Long genreId);
}
