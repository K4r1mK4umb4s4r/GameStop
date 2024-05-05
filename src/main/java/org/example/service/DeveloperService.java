package org.example.service;

import org.example.dao.DeveloperRepository;
import org.example.DTO.DeveloperDTO;
import org.example.mapper.DeveloperMapper;
import org.example.model.Developer;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public DeveloperDTO getDeveloperById(long id) {
        Developer developer = developerRepository.getDeveloperById(id);
        return DeveloperMapper.toDTO(developer);
    }

    public List<DeveloperDTO> getAllDevelopers() {
        List<Developer> developers = developerRepository.getAllDevelopers();
        return developers.stream().map(DeveloperMapper::toDTO).collect(Collectors.toList());
    }

    public void saveDeveloper(DeveloperDTO developerDTO) {
        Developer developer = DeveloperMapper.toEntity(developerDTO);
        developerRepository.saveDeveloper(developer);
    }

    public void updateDeveloper(DeveloperDTO developerDTO) {
        Developer developer = DeveloperMapper.toEntity(developerDTO);
        developerRepository.updateDeveloper(developer);
    }

    public void deleteDeveloper(Long id) {
        developerRepository.deleteDeveloper(id);
    }
    public List<DeveloperDTO> findDevelopersByName(String name) {
        List<Developer> developers = developerRepository.findDevelopersByName(name);
        return developers.stream().map(DeveloperMapper::toDTO).collect(Collectors.toList());
    }
}



