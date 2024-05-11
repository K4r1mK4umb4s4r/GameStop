package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.GameGenreRepository;
import org.example.DTO.GameGenreDTO;
import org.example.mapper.GameGenreMapper;
import org.example.model.GameGenre;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GameGenreService {
    private final GameGenreRepository gameGenreRepository;
    private static final Logger logger = LogManager.getLogger(GameGenreService.class);

    public GameGenreService(GameGenreRepository gameGenreRepository) {
        this.gameGenreRepository = gameGenreRepository;
    }

    public void addGenreToGame(GameGenreDTO gameGenreDTO) {
        try {
            GameGenre gameGenre = GameGenreMapper.toEntity(gameGenreDTO);
            gameGenreRepository.addGenreToGame(gameGenre.getGameId(), gameGenre.getGenreId());
        } catch (Exception e) {
            logger.error("Error adding genre to game", e);
            throw e;
        }
    }

    public void removeGenreFromGame(GameGenreDTO gameGenreDTO) {
        try {
            GameGenre gameGenre = GameGenreMapper.toEntity(gameGenreDTO);
            gameGenreRepository.removeGenreFromGame(gameGenre.getGameId(), gameGenre.getGenreId());
        } catch (Exception e) {
            logger.error("Error removing genre from game", e);
            throw e;
        }
    }

    public List<GameGenreDTO> getGenresByGameId(Long gameId) {
        try {
            List<GameGenre> gameGenres = gameGenreRepository.getGenresByGameId(gameId);
            return gameGenres.stream().map(GameGenreMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving genres by game ID", e);
            throw e;
        }
    }
}
