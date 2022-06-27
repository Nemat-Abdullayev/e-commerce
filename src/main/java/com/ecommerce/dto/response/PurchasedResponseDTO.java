package com.ecommerce.dto.response;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for purchased response")
public class PurchasedResponseDTO {

    private Double spent;
    private List<Integer> change;
}
