package com.ecommerce.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for deposit request")
public class DepositRequestDTO {

    @ApiModelProperty("deposit should be like [ 5 , 10 , 20 , 50 , 100 ")
    @NotNull(message = "deposit cannot be null")
    private Double deposit;
}
