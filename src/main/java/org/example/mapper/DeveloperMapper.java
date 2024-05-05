package org.example.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.example.DTO.DeveloperDTO;
import org.example.ServletDTO.DeveloperSDTO;
import org.example.model.Developer;

public class DeveloperMapper {

    public static DeveloperDTO toDTO(Developer developer) {
        if (developer == null) {
            return null;
        }
        return new DeveloperDTO(developer.getDeveloperId(), developer.getTitle(), developer.getWebsite());
    }

    public static DeveloperDTO toDTO(DeveloperSDTO developer) {
        if (developer == null) {
            return null;
        }
        return new DeveloperDTO(developer.getDeveloperId(), developer.getTitle(), developer.getWebsite());
    }

    public static DeveloperSDTO toSDTO(DeveloperDTO developer) {
        if (developer == null) {
            return null;
        }
        return new DeveloperSDTO(developer.getDeveloperId(), developer.getTitle(), developer.getWebsite());
    }


    public static Developer toEntity(DeveloperDTO dto) {
        if (dto == null) {
            return null;
        }
        Developer developer = new Developer();
        developer.setDeveloperId(dto.getDeveloperId());
        developer.setTitle(dto.getTitle());
        developer.setWebsite(dto.getWebsite());
        return developer;
    }


    public static DeveloperDTO fromRequest(HttpServletRequest request) {
        Long developerId = Long.parseLong(request.getParameter("developerId"));
        String title = request.getParameter("title");
        String website = request.getParameter("website");
        return new DeveloperDTO(developerId, title, website);
    }


    public static void toRequest(DeveloperDTO dto, HttpServletRequest request) {
        request.setAttribute("developerId", dto.getDeveloperId());
        request.setAttribute("title", dto.getTitle());
        request.setAttribute("website", dto.getWebsite());
    }
}
