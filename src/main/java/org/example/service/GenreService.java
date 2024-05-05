package org.example.service;

import org.example.dao.GenreRepository;
import org.example.DTO.GenreDTO;
import org.example.mapper.GenreMapper;
import org.example.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public GenreDTO getGenreById(Long genreId) {
        Genre genre = genreRepository.getGenreById(genreId);
        return GenreMapper.toDTO(genre);
    }

    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreRepository.getAllGenres();
        return genres.stream().map(GenreMapper::toDTO).collect(Collectors.toList());
    }

    public void saveGenre(GenreDTO genreDTO) {
        Genre genre = GenreMapper.toEntity(genreDTO);
        genreRepository.saveGenre(genre);
    }

    public void updateGenre(GenreDTO genreDTO) {
        Genre genre = GenreMapper.toEntity(genreDTO);
        genreRepository.updateGenre(genre);
    }

    public void deleteGenre(Long genreId) {
        genreRepository.deleteGenre(genreId);
    }
}
