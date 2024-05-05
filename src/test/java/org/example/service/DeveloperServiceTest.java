package org.example.service;

import org.example.DTO.DeveloperDTO;
import org.example.dao.DeveloperRepository;
import org.example.mapper.DeveloperMapper;
import org.example.model.Developer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeveloperServiceTest {
    private DeveloperService developerService;
    private DeveloperRepository developerRepository;

    @BeforeEach
    void setUp() {
        developerRepository = mock(DeveloperRepository.class);
        developerService = new DeveloperService(developerRepository);
    }

    @Test
    void getDeveloperById() {
        Developer expectedDeveloper = new Developer(1L, "Test Developer", "http://example.com");
        when(developerRepository.getDeveloperById(1)).thenReturn(expectedDeveloper);

        DeveloperDTO result = developerService.getDeveloperById(1);
        assertNotNull(result);
        assertEquals("Test Developer", result.getTitle());

        verify(developerRepository).getDeveloperById(1);
    }

    @Test
    void getAllDevelopers() {
        List<Developer> expectedDevelopers = Arrays.asList(new Developer(1L, "Dev One", "http://devone.com"));
        when(developerRepository.getAllDevelopers()).thenReturn(expectedDevelopers);

        List<DeveloperDTO> result = developerService.getAllDevelopers();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Dev One", result.get(0).getTitle());

        verify(developerRepository).getAllDevelopers();
    }

    @Test
    void saveDeveloper() {
        DeveloperDTO newDeveloper = new DeveloperDTO(2L, "New Dev", "http://newdev.com");
        doNothing().when(developerRepository).saveDeveloper(DeveloperMapper.toEntity(newDeveloper));

        developerService.saveDeveloper(new DeveloperDTO());
        verify(developerRepository).saveDeveloper(DeveloperMapper.toEntity(newDeveloper));
    }

    @Test
    void updateDeveloper() {
        DeveloperDTO updatedDeveloper = new DeveloperDTO(1L, "Updated Dev", "http://updateddev.com");
        doNothing().when(developerRepository).updateDeveloper(DeveloperMapper.toEntity(updatedDeveloper));

        developerService.updateDeveloper(updatedDeveloper);
        verify(developerRepository).updateDeveloper(DeveloperMapper.toEntity(updatedDeveloper));
    }

    @Test
    void deleteDeveloper() {
        doNothing().when(developerRepository).deleteDeveloper(1L);

        developerService.deleteDeveloper(1L);
        verify(developerRepository).deleteDeveloper(1L);
    }
}