package org.example.dao;

import org.example.model.Developer;

import java.util.List;

public interface DeveloperRepository {
    Developer getDeveloperById(long developerId);

    List<Developer> getAllDevelopers();

    void saveDeveloper(Developer developer);

    void updateDeveloper(Developer developer);

    void deleteDeveloper(Long developerId);

    List<Developer> findDevelopersByName(String name);
}
