package org.example.DTO;

public class DeveloperDTO {
    private Long developerId;
    private String title;
    private String website;

    public DeveloperDTO() {
    }

    public DeveloperDTO(Long developerId, String title, String website) {
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
    public String toString() {
        return "DeveloperDTO{" +
                "developerId=" + developerId +
                ", title='" + title + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
