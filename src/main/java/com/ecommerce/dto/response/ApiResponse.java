package com.ecommerce.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("model for general response of api")
public class ApiResponse<T> {

    private T data;
    private boolean success;


    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(boolean success) {
        this.success = success;
    }

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
