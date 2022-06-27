package com.ecommerce.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("update model for user")
public class UserUpdateRequestDTO {

    @NotNull(message = "user id cannot be null")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "username must be alphanumeric")
    @NotNull(message = "username cannot be null")
    private String username;

    @Length(min = 2, message = "password Length must be 2 or greater than")
    private String password;

    private double deposit;

    @NotNull(message = "role id cannot be null")
    private Long roleId;
}
