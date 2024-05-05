package org.example.DTO;

public class GameGenreDTO {
    private Long gameId;
    private Long genreId;

    public GameGenreDTO() {
    }

    public GameGenreDTO(Long gameId, Long genreId) {
        this.gameId = gameId;
        this.genreId = genreId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

}
