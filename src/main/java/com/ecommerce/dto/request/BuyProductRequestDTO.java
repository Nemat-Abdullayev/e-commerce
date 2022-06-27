package com.ecommerce.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for buy product request")
public class BuyProductRequestDTO {

    private Long productId;
    private int countOfProducts;
}
