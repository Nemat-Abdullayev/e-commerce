package com.ecommerce.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for user response")
public class UserResponseDTO {

    private Long id;
    private String username;
    private String password;
    private double deposit;
    private Long roleId;
}
