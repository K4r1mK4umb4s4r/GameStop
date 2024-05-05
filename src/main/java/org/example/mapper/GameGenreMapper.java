package org.example.mapper;

import org.example.DTO.GameGenreDTO;
import org.example.model.GameGenre;

public class GameGenreMapper {

    public static GameGenreDTO toDTO(GameGenre gameGenre) {
        return new GameGenreDTO(gameGenre.getGameId(), gameGenre.getGenreId());
    }

    public static GameGenre toEntity(GameGenreDTO dto) {
        return new GameGenre(dto.getGameId(), dto.getGenreId());
    }
}
