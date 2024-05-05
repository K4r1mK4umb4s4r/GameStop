package org.example.mapper;

import org.example.DTO.UserDTO;
import org.example.ServletDTO.UserSDTO;
import org.example.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getDateOfRegistration());
    }

    public static UserDTO toDTO(UserSDTO user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getDateOfRegistration());
    }

    public static UserSDTO toSDTO(UserDTO user) {
        return new UserSDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getDateOfRegistration());
    }

    public static User toEntity(UserDTO dto) {
        return new User(
                dto.getUserId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getDateOfRegistration());
    }
}
