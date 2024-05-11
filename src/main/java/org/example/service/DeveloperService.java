package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.DeveloperRepository;
import org.example.DTO.DeveloperDTO;
import org.example.mapper.DeveloperMapper;
import org.example.model.Developer;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private static final Logger logger = LogManager.getLogger(DeveloperService.class);

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public DeveloperDTO getDeveloperById(long id) {
        try {
            Developer developer = developerRepository.getDeveloperById(id);
            return DeveloperMapper.toDTO(developer);
        } catch (Exception e) {
            logger.error("Error getting developer by ID: " + id, e);
            throw e;
        }
    }

    public List<DeveloperDTO> getAllDevelopers() {
        try {
            List<Developer> developers = developerRepository.getAllDevelopers();
            return developers.stream().map(DeveloperMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting all developers", e);
            throw e;
        }
    }

    public void saveDeveloper(DeveloperDTO developerDTO) {
        try {
            Developer developer = DeveloperMapper.toEntity(developerDTO);
            developerRepository.saveDeveloper(developer);
        } catch (Exception e) {
            logger.error("Error saving developer", e);
            throw e;
        }
    }

    public void updateDeveloper(DeveloperDTO developerDTO) {
        try {
            Developer developer = DeveloperMapper.toEntity(developerDTO);
            developerRepository.updateDeveloper(developer);
        } catch (Exception e) {
            logger.error("Error updating developer", e);
            throw e;
        }
    }

    public void deleteDeveloper(Long id) {
        try {
            developerRepository.deleteDeveloper(id);
        } catch (Exception e) {
            logger.error("Error deleting developer with ID: " + id, e);
            throw e;
        }
    }

    public List<DeveloperDTO> findDevelopersByName(String name) {
        try {
            List<Developer> developers = developerRepository.findDevelopersByName(name);
            return developers.stream().map(DeveloperMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error finding developers by name: " + name, e);
            throw e;
        }
    }
}