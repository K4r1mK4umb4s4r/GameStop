package org.example.model;

import java.util.Objects;

public class Developer {
    private Long developerId;
    private String title;
    private String website;

    public Developer() {
    }

    public Developer(Long developerId, String title, String website) {
        this.developerId = developerId;
        this.title = title;
        this.website = website;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(developerId, developer.developerId) && Objects.equals(title, developer.title) && Objects.equals(website, developer.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId, title, website);
    }
}
