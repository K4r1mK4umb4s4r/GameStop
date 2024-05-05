package org.example.model;

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

}
