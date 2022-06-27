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
@ApiModel("model for product request")
public class ProductRequestDTO {

    private Long productId;

    @ApiModelProperty("cost should be in multiples of 5")
    private double cost;
    @NotNull(message = "product name cannot be null")
    private String productName;
    @NotNull(message = "amount available cannot be null")
    private Integer amountAvailable;
}
