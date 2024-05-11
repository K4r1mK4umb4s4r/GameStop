package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.GenreRepository;
import org.example.DTO.GenreDTO;
import org.example.mapper.GenreMapper;
import org.example.model.Genre;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GenreService {
    private final GenreRepository genreRepository;
    private static final Logger logger = LogManager.getLogger(GenreService.class);

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public GenreDTO getGenreById(Long genreId) {
        try {
            Genre genre = genreRepository.getGenreById(genreId);
            return GenreMapper.toDTO(genre);
        } catch (Exception e) {
            logger.error("Error getting genre by ID: " + genreId, e);
            throw e;
        }
    }

    public List<GenreDTO> getAllGenres() {
        try {
            List<Genre> genres = genreRepository.getAllGenres();
            return genres.stream().map(GenreMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting all genres", e);
            throw e;
        }
    }

    public void saveGenre(GenreDTO genreDTO) {
        try {
            Genre genre = GenreMapper.toEntity(genreDTO);
            genreRepository.saveGenre(genre);
        } catch (Exception e) {
            logger.error("Error saving genre", e);
            throw e;
        }
    }

    public void updateGenre(GenreDTO genreDTO) {
        try {
            Genre genre = GenreMapper.toEntity(genreDTO);
            genreRepository.updateGenre(genre);
        } catch (Exception e) {
            logger.error("Error updating genre", e);
            throw e;
        }
    }

    public void deleteGenre(Long genreId) {
        try {
            genreRepository.deleteGenre(genreId);
        } catch (Exception e) {
            logger.error("Error deleting genre", e);
            throw e;
        }
    }
}
