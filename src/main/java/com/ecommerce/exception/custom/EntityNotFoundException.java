package com.ecommerce.exception.custom;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public class EntityNotFoundException extends RuntimeException{


    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), searchParamsMap));
    }

    private static String generateMessage(String entity, String... searchParams) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                Arrays.toString(searchParams);
    }
}
