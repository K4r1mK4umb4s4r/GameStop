package org.example.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(genreId, genre.genreId) && Objects.equals(title, genre.title) && Objects.equals(description, genre.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, title, description);
    }
}
