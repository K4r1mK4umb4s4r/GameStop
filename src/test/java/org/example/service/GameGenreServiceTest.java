package org.example.service;

import org.example.dao.GameGenreRepository;
import org.example.DTO.GameGenreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class GameGenreServiceTest {
    @Mock
    private GameGenreRepository gameGenreRepository;

    @InjectMocks
    private GameGenreService gameGenreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addGenreToGame() {
        GameGenreDTO gameGenreDTO = new GameGenreDTO();
        gameGenreDTO.setGameId(1L);
        gameGenreDTO.setGenreId(2L);

        doNothing().when(gameGenreRepository).addGenreToGame(1L, 2L);

        gameGenreService.addGenreToGame(gameGenreDTO);

        verify(gameGenreRepository).addGenreToGame(gameGenreDTO.getGameId(), gameGenreDTO.getGenreId());
    }

    @Test
    void removeGenreFromGame() {
        GameGenreDTO gameGenreDTO = new GameGenreDTO();
        gameGenreDTO.setGameId(1L);
        gameGenreDTO.setGenreId(2L);
        gameGenreService.removeGenreFromGame(gameGenreDTO);

        verify(gameGenreRepository).removeGenreFromGame(1L, 2L);
    }

    @Test
    void getGenresByGameId() {
        gameGenreService.getGenresByGameId(1L);

        verify(gameGenreRepository).getGenresByGameId(1L);
    }
}
