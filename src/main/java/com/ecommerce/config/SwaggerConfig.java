package com.ecommerce.config;

import com.google.common.collect.Lists;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Import(springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> globalParameterList() {

        val authTokenHeader =
                new ParameterBuilder()
                        .name("Authorization") // name of the header
                        .modelRef(new ModelRef("string")) // data-type of the header
                        .required(false) // required/optional
                        .parameterType("header") // for query-param, this value can be 'query'
                        .description("Bearer +Token")
                        .build();

        return Collections.singletonList(authTokenHeader);
    }
}