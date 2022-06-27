package com.ecommerce.exception.model;
import com.ecommerce.util.LowerCaseClassNameResolver;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT,
        use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {


    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;
    private String message;
    private String uri;


    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String uri) {
        this();
        this.status = status;
        this.uri = uri;
    }

    public ApiError(HttpStatus status, Throwable ex, String uri) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.uri = uri;
    }

    public ApiError(HttpStatus status, String message, Throwable ex, String uri) {
        this();
        this.status = status;
        this.message = message;
        this.uri = uri;
    }


    public HttpStatus getStatus() {
        return status;
    }
}
