package com.ecommerce.mapper;

import com.ecommerce.dto.request.ProductRequestDTO;
import com.ecommerce.dto.response.ProductResponseDTO;
import com.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {
    public static ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract Product mapToEntity(ProductRequestDTO productRequestDTO);

    @Mappings({
            @Mapping(target = "created", expression = "java(parseLocalDateTime(product.getCreateDate()))")
    })
    public abstract ProductResponseDTO mapToDto(Product product);

    public String parseLocalDateTime(LocalDateTime endDate) {
        return endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}
