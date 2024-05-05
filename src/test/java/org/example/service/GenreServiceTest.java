package org.example.service;

import org.example.dao.GenreRepository;
import org.example.DTO.GenreDTO;
import org.example.mapper.GenreMapper;
import org.example.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGenreById() {
        Genre genre = new Genre(1L, "Action", "Action games involving...");
        GenreDTO genreDTO = new GenreDTO(1L, "Action", "Action games involving...");
        when(genreRepository.getGenreById(1L)).thenReturn(genre);
        when(genreMapper.toDTO(genre)).thenReturn(genreDTO);

        GenreDTO result = genreService.getGenreById(1L);
        assertNotNull(result);
        assertEquals("Action", result.getTitle());

        verify(genreRepository).getGenreById(1L);
        verify(genreMapper).toDTO(genre);
    }

    @Test
    void getAllGenres() {
        List<Genre> genres = Arrays.asList(new Genre(1L, "Action", "Description"));
        List<GenreDTO> genreDTOs = Arrays.asList(new GenreDTO(1L, "Action", "Description"));
        when(genreRepository.getAllGenres()).thenReturn(genres);


        List<GenreDTO> result = genreService.getAllGenres();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Action", result.get(0).getTitle());

        verify(genreRepository).getAllGenres();

    }

    @Test
    void saveGenre() {
        GenreDTO genreDTO = new GenreDTO(null, "Strategy", "Strategy games...");
        Genre genre = new Genre(null, "Strategy", "Strategy games...");
        doNothing().when(genreRepository).saveGenre(any(Genre.class));
        when(genreMapper.toEntity(genreDTO)).thenReturn(genre);

        genreService.saveGenre(genreDTO);

        verify(genreRepository).saveGenre(genre);
        verify(genreMapper).toEntity(genreDTO);
    }

    @Test
    void updateGenre() {
        GenreDTO genreDTO = new GenreDTO(1L, "Updated Genre", "Updated Description");
        Genre genre = new Genre(1L, "Updated Genre", "Updated Description");
        doNothing().when(genreRepository).updateGenre(any(Genre.class));
        when(genreMapper.toEntity(genreDTO)).thenReturn(genre);

        genreService.updateGenre(genreDTO);

        verify(genreRepository).updateGenre(genre);
        verify(genreMapper).toEntity(genreDTO);
    }

    @Test
    void deleteGenre() {
        doNothing().when(genreRepository).deleteGenre(1L);

        genreService.deleteGenre(1L);

        verify(genreRepository).deleteGenre(1L);
    }
}