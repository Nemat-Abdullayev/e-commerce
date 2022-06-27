package com.ecommerce.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for refresh token")
public class RefreshTokenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 270383254869437136L;

    @NotNull(message = "refresh token cannot be null")
    @NotBlank(message = "refresh token cannot be blank")
    @NotEmpty(message = "refresh token cannot be empty")
    private String refreshToken;
}
