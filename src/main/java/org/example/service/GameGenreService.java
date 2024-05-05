package org.example.service;

import org.example.dao.GameGenreRepository;
import org.example.DTO.GameGenreDTO;
import org.example.mapper.GameGenreMapper;
import org.example.model.GameGenre;

import java.util.List;
import java.util.stream.Collectors;

public class GameGenreService {
    private final GameGenreRepository gameGenreRepository;

    public GameGenreService(GameGenreRepository gameGenreRepository) {
        this.gameGenreRepository = gameGenreRepository;
    }

    public void addGenreToGame(GameGenreDTO gameGenreDTO) {
        GameGenre gameGenre = GameGenreMapper.toEntity(gameGenreDTO);
        gameGenreRepository.addGenreToGame(gameGenre.getGameId(), gameGenre.getGenreId());
    }

    public void removeGenreFromGame(GameGenreDTO gameGenreDTO) {
        GameGenre gameGenre = GameGenreMapper.toEntity(gameGenreDTO);
        gameGenreRepository.removeGenreFromGame(gameGenre.getGameId(), gameGenre.getGenreId());
    }

    public List<GameGenreDTO> getGenresByGameId(Long gameId) {
        List<GameGenre> gameGenres = gameGenreRepository.getGenresByGameId(gameId);
        return gameGenres.stream().map(GameGenreMapper::toDTO).collect(Collectors.toList());
    }
}
