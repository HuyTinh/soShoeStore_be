package com.personal.soshoestore_be.mapper;

import com.personal.soshoestore_be.dto.UserRegisterDTO;
import com.personal.soshoestore_be.dto.UserUpdateContactDTO;
import com.personal.soshoestore_be.dto.UserUpdateProfileDTO;
import com.personal.soshoestore_be.model.User;
import com.personal.soshoestore_be.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    @Mapping(target="role", expression = "java(user.getRole().getName())")
    public abstract UserResponse toResponse(User user);

    public abstract User toEntity(UserUpdateProfileDTO userUpdateProfileDTO);

    public abstract User toEntity(UserRegisterDTO userRegisterDTO);

    public User mapToEntity(UserUpdateProfileDTO userUpdateProfileDTO, User user) {
        user.setFirstName(userUpdateProfileDTO.getFirstName());
        user.setLastName(userUpdateProfileDTO.getLastName());
        user.setDateOfBirth(userUpdateProfileDTO.getDateOfBirth());
        return user;
    };
    public User mapToEntity(UserUpdateContactDTO userUpdateContactDTO, User user) {
        user.setPhoneNumber(userUpdateContactDTO.getPhoneNumber());
        user.setAddress(userUpdateContactDTO.getAddress());
        return user;
    };
}
