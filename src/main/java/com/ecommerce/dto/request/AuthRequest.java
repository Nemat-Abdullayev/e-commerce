package com.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private static final long serialVersionUID = 270383254869437136L;

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    @Length(min = 2,message = "password length must be 2 or greater than")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "password must be alphanumeric")
    private String password;
}
