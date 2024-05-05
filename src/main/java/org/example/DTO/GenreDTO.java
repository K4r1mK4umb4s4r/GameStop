package org.example.DTO;

public class GenreDTO {
    private Long genreId;
    private String title;
    private String description;

    public GenreDTO() {
    }

    public GenreDTO(Long genreId, String title, String description) {
        this.genreId = genreId;
        this.title = title;
        this.description = description;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
