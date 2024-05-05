package org.example.service;

import org.example.dao.GenreRepository;
import org.example.DTO.GenreDTO;
import org.example.model.Genre;
import org.example.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGenreById() {
        Long genreId = 1L;
        Genre genre = new Genre(genreId, "Horror", "Scary genre");
        when(genreRepository.getGenreById(genreId)).thenReturn(genre);

        GenreDTO result = genreService.getGenreById(genreId);

        assertNotNull(result);
        assertEquals(genreId, result.getGenreId());
        assertEquals("Horror", result.getTitle());
    }

    @Test
    public void testGetAllGenres() {
        Genre genre1 = new Genre(1L, "Horror", "Scary genre");
        Genre genre2 = new Genre(2L, "Comedy", "Funny genre");
        when(genreRepository.getAllGenres()).thenReturn(Arrays.asList(genre1, genre2));

        List<GenreDTO> genres = genreService.getAllGenres();

        assertNotNull(genres);
        assertEquals(2, genres.size());
        assertEquals("Horror", genres.get(0).getTitle());
        assertEquals("Comedy", genres.get(1).getTitle());
    }

    @Test
    public void testSaveGenre() {
        GenreDTO genreDTO = new GenreDTO(null, "Horror", "Scary genre");
        Genre genre = GenreMapper.toEntity(genreDTO);

        doNothing().when(genreRepository).saveGenre(genre);

        genreService.saveGenre(genreDTO);

        verify(genreRepository).saveGenre(genre);
    }

    @Test
    public void testUpdateGenre() {
        GenreDTO genreDTO = new GenreDTO(1L, "Updated Horror", "Updated description");
        Genre genre = GenreMapper.toEntity(genreDTO);

        doNothing().when(genreRepository).updateGenre(genre);

        genreService.updateGenre(genreDTO);

        verify(genreRepository).updateGenre(genre);
    }

    @Test
    public void testDeleteGenre() {
        Long genreId = 1L;
        doNothing().when(genreRepository).deleteGenre(genreId);

        genreService.deleteGenre(genreId);

        verify(genreRepository).deleteGenre(genreId);
    }
}
