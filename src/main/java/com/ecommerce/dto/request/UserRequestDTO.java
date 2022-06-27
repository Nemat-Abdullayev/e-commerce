package com.ecommerce.dto.request;

import com.ecommerce.enums.RoleName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for user")
public class UserRequestDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "username must be alphanumeric")
    @NotNull(message = "username cannot be null")
    private String username;

    @Length(min = 2, message = "password Length must be 2 or greater than")
    private String password;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty("role name mus be uppercase like #example [ SELLER, BUYER ] ")
    private RoleName role;


}
