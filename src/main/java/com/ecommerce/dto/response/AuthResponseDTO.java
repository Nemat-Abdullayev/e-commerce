package com.ecommerce.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for authentication response")
public class AuthResponseDTO implements Serializable {

    @ApiModelProperty("access token for authentication")
    private String accessToken;

    @ApiModelProperty("refresh token for authentication")
    private String refreshToken;
}
