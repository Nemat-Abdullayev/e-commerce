package com.ecommerce.service;

import com.ecommerce.dto.request.BuyProductRequestDTO;
import com.ecommerce.dto.request.ProductRequestDTO;
import com.ecommerce.dto.response.ApiResponse;

public interface ProductService {

    ApiResponse<?> create(ProductRequestDTO productRequestDTO);

    ApiResponse<?> findById(Long id);

    ApiResponse<?> findAll();

    ApiResponse<?> update(ProductRequestDTO productRequestDTO);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> buyProduct(BuyProductRequestDTO productRequestDTO);


}
