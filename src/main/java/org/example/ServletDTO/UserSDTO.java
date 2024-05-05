package org.example.ServletDTO;

import java.time.LocalDate;

public class UserSDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private LocalDate dateOfRegistration;

    public UserSDTO() {
    }

    public UserSDTO(Long userId, String name, String email, String password, LocalDate dateOfRegistration) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfRegistration = dateOfRegistration;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

}