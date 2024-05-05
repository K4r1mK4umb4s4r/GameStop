package org.example.model;

public class Genre {
    private Long genreId;
    private String title;
    private String description;

    public Genre() {}

    public Genre(Long genreId, String title, String description) {
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
