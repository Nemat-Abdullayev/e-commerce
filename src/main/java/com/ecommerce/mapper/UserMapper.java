package com.ecommerce.mapper;


import com.ecommerce.dto.request.UserRequestDTO;
import com.ecommerce.dto.response.UserResponseDTO;
import com.ecommerce.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    public abstract User mapToEntity(UserRequestDTO userRequestDTO);

    public abstract UserResponseDTO mapToDto(User user);
}
