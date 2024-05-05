package org.example.model;

public class GameGenre {
    private Long gameId;
    private Long genreId;

    public GameGenre() {
    }

    public GameGenre(Long gameId, Long genreId) {
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
