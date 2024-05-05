package org.example.mapper;

import org.example.DTO.GenreDTO;
import org.example.model.Genre;

public class GenreMapper {

    public static GenreDTO toDTO(Genre genre) {
        return new GenreDTO(
                genre.getGenreId(),
                genre.getTitle(),
                genre.getDescription());
    }

    public static Genre toEntity(GenreDTO dto) {
        return new Genre(
                dto.getGenreId(),
                dto.getTitle(),
                dto.getDescription());
    }
}
